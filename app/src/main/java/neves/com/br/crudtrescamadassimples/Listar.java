package neves.com.br.crudtrescamadassimples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

public class Listar extends AppCompatActivity implements AdapterView.OnItemClickListener, Serializable {

    CachorroBD cachorroBD;
    Cachorro cachorro;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
        cachorro=new Cachorro();
        cachorroBD= CachorroBD.getInstance(this);

        lista=(ListView) findViewById(R.id.listviewpersonalizado);
        lista.setOnItemClickListener(this);
        carregarListView(cachorroBD.getAll());

    }
    public void carregarListView(List<Cachorro> cachorros){
        //cria um objeto da classe ListAdapter, um adaptador List -> ListView
        ListAdapter dadosAdapter = new ListAdapter(this, cachorros);
        //associa o adaptador a ListView
        lista.setAdapter(dadosAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Long codigo;
        cachorro = (Cachorro) adapterView.getAdapter().getItem(i); //obtém o Cachorro
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
