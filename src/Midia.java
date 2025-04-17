// Classe abstrata para os tres tipos de mídia
public abstract class Midia {
    protected String titulo;
    protected String genero;
    protected int ano;
    protected int avaliacao;
    protected boolean consumido;
    protected String dataConsumo;

    public String getTitulo() { return titulo; }
    public int getAvaliacao() { return avaliacao; }
    public void setAvaliacao(int avaliacao) { this.avaliacao = avaliacao; }
    public void setConsumido(boolean consumido) { this.consumido = consumido; }
    public boolean isConsumido() { return consumido; }
    public String getGenero() { return genero; }
    public int getAno() { return ano; }
    public void setDataConsumo(String dataConsumo) { this.dataConsumo = dataConsumo; }
    public String getDataConsumo() { return dataConsumo; }

    @Override
    public String toString() {
        return String.format("Título: %s | Gênero: %s | Ano: %d | Avaliação: %d | Consumido: %s | Data: %s",
                titulo, genero, ano, avaliacao, consumido ? "Sim" : "Não", dataConsumo != null ? dataConsumo : "N/A");
    }
}