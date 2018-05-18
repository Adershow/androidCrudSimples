package neves.com.br.crudtrescamadassimples;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

public class Cadastrar extends AppCompatActivity {
    public EditText nome, raca, sexo, tamanho;
    public ImageView IVimagem;
    private byte[] imagem;
    Button btsalvar;
    Button btcancelar;
    private CachorroBD cachorroBD;
    private Cachorro cachorro;
    private static String TAG = "cahorros_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        cachorro = new Cachorro();
        cachorroBD = CachorroBD.getInstance(this);
        nome = (EditText) findViewById(R.id.eNome);
        sexo = (EditText) findViewById(R.id.eSexo);
        raca = (EditText) findViewById(R.id.eRaca);
        tamanho = (EditText) findViewById(R.id.tamanho);
        IVimagem = (ImageView) findViewById(R.id.imageView);
        btsalvar = (Button) findViewById(R.id.button);
        btcancelar = (Button) findViewById(R.id.button2);
        btsalvar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!nome.getText().toString().isEmpty() &&
                        !sexo.getText().toString().isEmpty() &&
                        !raca.getText().toString().isEmpty() &&
                        !tamanho.getText().toString().isEmpty()) {
                    if (cachorro._id == null) { //se é uma inclusão
                        cachorro = new Cachorro(); //apaga dados antigos
                    }

                    cachorro.nome = nome.getText().toString();
                    cachorro.sexo = sexo.getText().toString();
                    cachorro.raca = raca.getText().toString();
                    cachorro.tamanho = tamanho.getText().toString();
                    cachorro.imagem = imagem;
                    Log.d(TAG, "Cachorro que será salvo: " + cachorro.toString());
                   // new Task().execute(SAVE);
                    cachorroBD.save(cachorro);
                    Toast.makeText(Cadastrar.this, "Cachorro Salvo com Sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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


