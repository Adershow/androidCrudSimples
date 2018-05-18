package neves.com.br.crudtrescamadassimples;

import java.io.Serializable;

/**
 * Created by diego on 13/10/2017.
 */
public class Cachorro implements Serializable{
    private static final long serialVersionUID = 1L;

    public Long _id;
    public String nome;
    public String raca;
    public String sexo;
    public String tamanho;
    public byte[] imagem;

    @Override
    public String toString() {
        return "Cachorro{" +
                "_id=" + _id +
                ", nome='" + nome + '\'' +
                ", raca='" + raca + '\'' +
                ", sexo='" + sexo + '\'' +
                ", tamanho='" + tamanho + '\'' +
                ", imagem='" + imagem + '\'' +
                '}';
    }

    public String lista(){
        return "Nome={" + nome+'}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
