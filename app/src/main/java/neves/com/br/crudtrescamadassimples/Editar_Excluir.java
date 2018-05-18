package neves.com.br.crudtrescamadassimples;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.util.Locale;

public class Editar_Excluir extends AppCompatActivity{
    String frase;
    private TextToSpeech tts;
    public EditText nome, raca, sexo, tamanho;
    public ImageView IVimagem;
    private byte[] imagem;
    TextView aliascodigo;
    Button btsalvar, btcancelar;
    private CachorroBD cachorroBD;
    private Cachorro cachorro;
    private String TAG = "editarTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar__excluir);
        cachorro = new Cachorro();
        cachorroBD = CachorroBD.getInstance(this);
        nome = (EditText) findViewById(R.id.eNome);
        sexo = (EditText) findViewById(R.id.eSexo);
        raca = (EditText) findViewById(R.id.eRaca);
        tamanho = (EditText) findViewById(R.id.tamanho);
        IVimagem = (ImageView) findViewById(R.id.imageView);
        aliascodigo=(TextView)findViewById(R.id.txtcodigo);
        btsalvar = (Button) findViewById(R.id.button);
        btcancelar = (Button) findViewById(R.id.button2);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Cachorro registro = (Cachorro) intent.getSerializableExtra("Objeto");
        aliascodigo.setText(registro._id.toString());
        nome.setText(registro.nome);
        raca.setText(registro.raca);
        sexo.setText(registro.sexo);
        tamanho.setText(registro.tamanho);
        if (registro.imagem != null){
            IVimagem.setImageBitmap(BitmapFactory.decodeByteArray(registro.imagem, 0, registro.imagem.length));

        }else {
            IVimagem.setImageResource(R.drawable.imagem);
        }
        //A parte da fala Beu Amigo
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.getDefault());
                falar();
            }
        });

        }

   public void salvar(View view){
       if (!nome.getText().toString().isEmpty() &&
               !sexo.getText().toString().isEmpty() &&
               !raca.getText().toString().isEmpty()) {
           cachorro._id=Long.parseLong(aliascodigo.getText().toString());
           cachorro.nome = nome.getText().toString();
           cachorro.sexo = sexo.getText().toString();
           cachorro.raca = raca.getText().toString();
           cachorro.tamanho = tamanho.getText().toString();
           cachorro.imagem = imagem;

           Log.d(TAG, "Contato que será salvo: " + cachorro.toString());

           // new Task().execute(SAVE);
           cachorroBD.save(cachorro);
           Toast.makeText(this, "Atualização de Cachorro Realizada", Toast.LENGTH_SHORT).show();
       } else {
           Toast.makeText(getBaseContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
       }
   }

    public void excluir(View view){
            cachorro._id=Long.parseLong(aliascodigo.getText().toString());

            Log.d(TAG, "Cachorro que será excluido: " + cachorro.toString());

            // new Task().execute(SAVE);
            cachorroBD.delete(cachorro);
        Toast.makeText(this, "Cachorro Excluido", Toast.LENGTH_SHORT).show();

        }
    public void falar(){
        frase = " Nome "+ nome.getText().toString() + " Raça " + raca.getText().toString() + " Sexo " +
                sexo.getText().toString() + " tamanho " + tamanho.getText().toString()+ " My Master";
        tts.speak(frase, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void carregarImagem(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione a sua imagem"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri arquivoUri = data.getData();
            Log.d(TAG, "Uri da imagem: " + arquivoUri);
            IVimagem.setImageURI(arquivoUri);
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(arquivoUri));
                bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true); //reduz e aplica um filtro na imagem
                byte[] img = getBitmapAsByteArray(bitmap); //converte para um fluxo de bytes
                imagem = img;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); //criam um stream para ByteArray
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream); //comprime a imagem
        return outputStream.toByteArray(); //retorna a imagem como um Array de Bytes (byte[])
    }

}





