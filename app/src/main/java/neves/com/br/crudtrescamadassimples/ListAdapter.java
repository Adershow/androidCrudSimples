package neves.com.br.crudtrescamadassimples;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Cachorro> {

    public Cachorro cachorro;
    public ListAdapter(Context context, List<Cachorro> cachorros) {
        super(context, 0, cachorros);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Lê os dados da posição que o usuário clicou
         cachorro = getItem(position);

        // Checa de está utilizando o padrão ViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_layout, parent, false);
        }
        // mapeia as views do layout do adaptador
        TextView tvNome = (TextView) convertView.findViewById(R.id.tv_item_nome);
        TextView tvRaca = (TextView) convertView.findViewById(R.id.tv_item_raca);
        TextView tvSexo = (TextView) convertView.findViewById(R.id.tv_item_sexo);
        TextView tvTamanho = (TextView) convertView.findViewById(R.id.tv_item_tamanho);
        ImageView imvImagem = (ImageView) convertView.findViewById(R.id.imv_item);
        if(cachorro.imagem != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(cachorro.imagem, 0, cachorro.imagem.length);
            imvImagem.setImageBitmap(bitmap);
        }else {
            imvImagem.setImageResource(R.drawable.imagem);
        }
        // popula as views
        tvNome.setText(cachorro.nome);
        tvRaca.setText(cachorro.raca);
        tvSexo.setText(cachorro.sexo);
        tvTamanho.setText(cachorro.tamanho);
              // retorna a view do item completa para renderizar no screen
        return convertView;
    }
}
