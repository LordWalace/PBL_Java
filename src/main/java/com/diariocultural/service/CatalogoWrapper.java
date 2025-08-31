// === CatalogoWrapper === "Service"

package com.diariocultural.service;

import com.diariocultural.model.Filme;
import com.diariocultural.model.Livro;
import com.diariocultural.model.Serie;
import java.util.List;

/**
 * Classe auxiliar usada apenas para estruturar o arquivo JSON de forma limpa,
 * separando os tipos de mídia durante a serialização.
 */
class CatalogoWrapper {
    List<Livro> livros;
    List<Filme> filmes;
    List<Serie> series;
}

/******************************************************************************************

 Autor: Walace de Jesus Venas
 Componente Curricular: EXA863 MI-PROGRAMAÇÃO
 Concluído em: 01/07/2025
 Declaro que este código foi elaborado por mim de forma individual e não contêm nenhum
 trecho de código de outro colega ou de outro autor, tais como provindos de livros e
 apostilas, e páginas ou documentos eletrônicos da Internet. Qualquer trecho de código
 de outra autoria que não a minha está destacado com uma citação para o autor e a fonte
 do código, e estou ciente que estes trechos não serão considerados para fins de avaliação.

 *******************************************************************************************/