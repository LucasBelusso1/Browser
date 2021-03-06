/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package browser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author daniel
 */
public class Parser {

    Pattern regex = Pattern.compile("<([A-z][A-z0-9]*)\\b([^>]*)>(.*?)<(\\/\\1)>|<(br)>(.*)|<(img)([^>]*)>");
    Pattern folha = Pattern.compile("(^[^<>]*[\\w\\s]+)");

    public No parse(String html, No pai) {
        No raiz = null;
        Matcher f = folha.matcher(html);
        if (f.find()) {
            Texto texto = new Texto(f.group(1), "");
            if (pai != null) {
                pai.inserir(texto);
            }
            return texto;
        }
        Matcher m = regex.matcher(html);
        String nomeTag = "";
        String atributos = "";
        String sobra = "";
        while (m.find()) {
            if (m.group(1) != null) {
                nomeTag = m.group(1);
                atributos = m.group(2);
                sobra = m.group(3);
            }
            if (m.group(5) != null) {
                nomeTag = m.group(5);
                sobra = m.group(6);
            }
            if (m.group(7) != null) {
                nomeTag = m.group(7);
                atributos = m.group(8);
            }
            Tag tag = new Tag(nomeTag, atributos);
            if (pai != null) {
                pai.inserir(tag);
            } else {
                raiz = tag;
            }
            parse(sobra, tag);
        }
        return raiz;
    }
}
