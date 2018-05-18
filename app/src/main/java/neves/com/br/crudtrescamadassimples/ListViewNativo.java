package neves.com.br.crudtrescamadassimples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class ListViewNativo extends AppCompatActivity implements AdapterView.OnItemClickListener, Serializable{

    CachorroBD cachorroBD;
    Cachorro cachorro;
    ListView lista;

    private String TAG = "cachorros_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        cachorro=new Cachorro();
        cachorroBD= CachorroBD.getInstance(this);

        lista=(ListView) findViewById(R.id.listviewview);
        lista.setOnItemClickListener(this);
        carregarListView(cachorroBD.getAll());
    }

    public void carregarListView(List<Cachorro> cachorros){
        // Lista Simples utilizando o layout nativo do Android
        ArrayAdapter<Cachorro> adaptador =new ArrayAdapter<Cachorro>(this, android.R.layout.simple_list_item_1,cachorros);
        lista.setAdapter(adaptador);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
        Long codigo;
        cachorro = (Cachorro) parent.getAdapter().getItem(i); //obtém o Cachorro
        Toast.makeText(this, cachorro.lista(), Toast.LENGTH_SHORT).show();

        Intent nova= new Intent(this, Editar_Excluir.class);
        nova.putExtra("Objeto", cachorro);
        startActivity(nova);
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListView(cachorroBD.getAll());
    }
}
