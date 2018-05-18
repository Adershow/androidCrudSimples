package neves.com.br.crudtrescamadassimples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener{
    String [] menulista = new String []{"Cadastrar", "ListViewNativo", "ListViewPersonalizada"};
    ListView listamenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listamenu= (ListView) findViewById(R.id.litview1);
        ArrayAdapter<String> adaptador= new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,menulista);
        listamenu.setAdapter(adaptador);
        listamenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:{
                Intent intent=new Intent(this, Cadastrar.class);
                startActivity(intent);
                break;
            }
            case 1:{
                Intent intent=new Intent(this, ListViewNativo.class);
                startActivity(intent);
                break;
            }
            case 2:{
                Intent intent=new Intent(this, Listar.class);
                startActivity(intent);
                break;
            }

        }
    }
}
