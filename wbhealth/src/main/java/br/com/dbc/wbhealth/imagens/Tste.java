package br.com.dbc.wbhealth.imagens;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Tste {
    public static void main(String[] args) {
        try (FileInputStream imageStream = new FileInputStream("src/main/java/br/com/dbc/wbhealth/imagens/Logo_WBHEALTH.png")) {
            byte[] imageBytes = new byte[imageStream.available()];
            imageStream.read(imageBytes);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
