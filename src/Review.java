// === Review.java === "Model"

import java.time.LocalDate;

public class Review {
    private String texto;
    private LocalDate dataCriacao;

    public Review(String texto) {
        this.texto = texto;
        this.dataCriacao = LocalDate.now();
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }
}