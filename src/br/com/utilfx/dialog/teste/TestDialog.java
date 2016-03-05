package br.com.utilfx.dialog.teste;

import br.com.utilfx.dialog.AlertDialog;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main de Teste.
 *
 * @author Heverton Cruz
 * @version 1.0
 */
public class TestDialog extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //AnchorPane root = new AnchorPane();
        //Scene scene = new Scene(root);
        //stage.setScene(scene);
        //stage.show();
        
        //----------------------------------------------------------------------
        /**
         * Implementação curta com método estático.
         */
        AlertDialog.open(stage, "Este é um teste de mensagem \n"
                + "que exibi o AlertDialog com o método estático.");
//        /**
//         * Implementação longa com manipulação dos componentes.
//         */
//        final AlertDialog alert = new AlertDialog(stage, "Este é um teste de mensagem \n"
//                + "que exibi o AlertDialog com uma instância.");
//        alert.setMessage("Este é um teste de mensagem alterada \n"
//                + "que exibi o AlertDialog com uma instância.");
//        
//        alert.getButton().setOnAction(new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent t) {
//                System.out.println("Sobreescrita da ação do botão do AlertDialog.");
//                alert.close();
//            }
//        });
//        alert.show();
//        //----------------------------------------------------------------------
//        /**
//         * Implementação curta com método estático.
//         */
//        int result1 = ConfirmDialog.open(stage, "Este é um teste de mensagem \n"
//                + "que exibi o  ConfirmDialog com o método estático."/*, new String[]{"Botão 1", "Botão 2"});//ConfirmDialogType.OK_CANCEL*/);
//        System.out.println(result1);
//        /**
//         * Implementação longa com manipulação dos componentes.
//         */
//        final ConfirmDialog confirm = new ConfirmDialog(stage, "Este é um teste de mensagem \n"
//                + "que exibi o  ConfirmDialog com com uma instância.");
//        confirm.setMessage("Este é um teste de mensagem alterada \n"
//                + "que exibi o ConfirmDialog com uma instância.");
//        confirm.getButtonLeft().setOnAction(new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent t) {
//                System.out.println("Sobreescrita da ação do botão esquerdo do ConfirmDialog.");
//                System.out.println(1);
//                confirm.close();
//            }
//        });
//        confirm.getButtonRight().setOnAction(new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent t) {
//                System.out.println("Sobreescrita da ação do botão direito do ConfirmDialog.");
//                System.out.println(0);
//                confirm.close();
//            }
//        });
//        confirm.show();
//        //----------------------------------------------------------------------
//        /**
//         * Implementação curta com método estático.
//         */
//        String result2 = PromptDialog.open(stage, "Este é um teste de mensagem \n"
//                + "que exibi o PromptDialog com o método estático.");
//        System.out.println(result2);
//        /**
//         * Implementação longa com manipulação dos componentes.
//         */
//        final PromptDialog prompt = new PromptDialog(stage, "Este é um teste de mensagem \n"
//                + "que exibi o PromptDialog com uma instância.");
//        prompt.setMessage("Este é um teste de mensagem alterada \n"
//                + "que exibi o PromptDialog com uma instância.");
//        
//        prompt.getTextField().setPromptText("Digite qualquer valor...");
//        
//        prompt.getButtonLeft().setOnAction(new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent t) {
//                System.out.println("Sobreescrita da ação do botão esquerdo do PromptDialog.");
//                System.out.println(prompt.getTextField().getText());
//                prompt.close();
//            }
//        });
//        prompt.getButtonRight().setOnAction(new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent t) {
//                System.out.println("Sobreescrita da ação do botão direito do PromptDialog.");
//                prompt.close();
//            }
//        });
//        prompt.show();
//        
//        //----------------------------------------------------------------------
//        /**
//        * Implementação curta com método estático.
//        */
//        String result3 = PasswordDialog.open(stage, "Este é um teste de mensagem \n"
//                + "que exibi o PasswordDialog com o método estático.");
//        System.out.println(result3);
//        /**
//         * Implementação longa com manipulação dos componentes.
//         */
//        final PasswordDialog password = new PasswordDialog(stage, "Este é um teste de mensagem \n"
//                + "que exibi o PasswordDialog com uma instância.");
//        password.setMessage("Este é um teste de mensagem alterada \n"
//                + "que exibi o PasswordDialog com uma instância.");
//        
//        password.getPasswordField().setText("Digite qualquer valor...");
//        
//        password.getButtonLeft().setOnAction(new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent t) {
//                System.out.println("Sobreescrita da ação do botão esquerdo do PasswordDialog.");
//                System.out.println(prompt.getTextField().getText());
//                prompt.close();
//            }
//        });
//        password.getButtonRight().setOnAction(new EventHandler<ActionEvent>(){
//            @Override
//            public void handle(ActionEvent t) {
//                System.out.println("Sobreescrita da ação do botão direito do PasswordDialog.");
//                prompt.close();
//            }
//        });
//        password.show();
//        
        //----------------------------------------------------------------------
    }

    public static void main(String[] args) {
        launch(args);
    }
}
