package br.com.utilfx.stage.control;

import br.com.utilfx.dialog.ConfirmDialog;
import br.com.utilfx.stage.control.fxcontrol.FXMLInitializable;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * Faz o controle de abertura de Frames (Stages)
 *
 * @author Heverton Cruz
 * @author Heverton Cruz
 * @version 1.1
 */
public class StageMDIControl {

    /**
     * Instância da classe para o Singleton
     */
    private static StageMDIControl instance = null;
    /**
     * Armazena a seguência de foco dos stages para permitir o atalho para
     * mudança de foco.
     */
    private ArrayList<Stage> stages = new ArrayList();
    /**
     * Registra todos os objetos Stage instanciados, indexados pelo Class do
     * controlador do arquivo FXML.
     */
    private HashMap<Class, Stage> containerStages = new HashMap();
    /**
     * Registra todos os objetos FXMLLoader instanciados, indexados pelo Class
     * do controlador do arquivo FXML.
     */
    private HashMap<Class, FXMLLoader> containerFXMLLoader = new HashMap();
    /**
     * Armazena a instânica do stage principal.
     */
    private Stage stageMain;
    /**
     * Armazena o objeto de imagem do favicon.
     */
    private Image favicon;

    /**
     * Construtor privado do Singleton.
     */
    private StageMDIControl() {
    }

    /**
     * Pega a única instância de StageMDIControl
     *
     * @return StageMDIControl
     */
    public static StageMDIControl getInstance() {
        if (instance == null) {
            instance = new StageMDIControl();
        }

        return instance;
    }

    /**
     * Seta o Stage principal da aplicação e lhe atribui alguns Eventos padrões.
     *
     * @param stageMain
     */
    public void setStageMain(final Stage stageMain) {
        //Executado antes de fechar o Stage por ação externa
        stageMain.setOnCloseRequest(createEventHandlerClosing(stageMain));
        //Adidiciona um KeyEvent
        addKeyEvent(stageMain);
        //Atribui ao escopo
        this.stageMain = stageMain;
    }

    /**
     * Cria um objeto EventHandler<WindowEvent> para ser usado no encerramento
     * da aplicação.
     *
     * @param Stage stage
     * @return EventHandler<WindowEvent>
     */
    private EventHandler<WindowEvent> createEventHandlerClosing(final Stage stage) {
        return new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                                
                //Impede o encerramento do Stage
                final ConfirmDialog confirm = new ConfirmDialog(stage, "Deseja realmente encerrar o sistema?");

                confirm.getButtonLeft().setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            //Exclui os handles atribuidos aos eventos
                            stage.setOnCloseRequest(null);
                            //Fecha o option
                            confirm.close();
                            //Fecha todos os Frames abertos
                            closeAll();
                            //Fecha a aplicação
                            stage.close();
                            //Encerrar processo do Java                        
                            Platform.exit();
                            System.exit(0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            System.exit(0);
                        }
                    }
                });

                confirm.show();

                //Interrompe a propagação do Evento
                event.consume();
            }
        };
    }

    /**
     * Retorna o Stage principal da aplicação.
     *
     * @return Stage
     */
    public Stage getStageMain() {
        return stageMain;
    }

    /**
     * Configura um ícone para a janela do sistema.
     *
     * @param favicon
     */
    public void setFavicon(Image favicon) {
        this.favicon = favicon;
    }

    /**
     * Abre o Frame com um FXML dentro a partir do Class de seu controlador e
     * faz com que haja apenas uma instâncias de cada frame.
     *
     * Obs.: Deve-se obedecer o padrão de nomeclatura nos pacotes, classes de
     * controle e arquivos fxml de visão e controle para que este método tenha o
     * resultado esperado. Os pacotes de visão e controle devem estar no mesmo
     * nível, devem ser nomeados com "view" e "control" sucessivamente e seus
     * nomes devem seguir o padrão CamelCase. Ex.:
     * projeto.modulo.view.Arquivo.fxml
     * projeto.modulo.control.ArquivoControl.java
     *
     * @param controller
     * @param title
     * @throws java.io.IOException
     */
    public void openFrame(final Class controller, String title) throws IOException {
        openFrame(controller, title, StageStyle.DECORATED, Modality.NONE);
    }

    /**
     * Abre o Frame com um FXML dentro a partir do Class de seu controlador e
     * faz com que haja apenas uma instâncias de cada frame.
     *
     * Obs.: Deve-se obedecer o padrão de nomeclatura nos pacotes, classes de
     * controle e arquivos fxml de visão e controle para que este método tenha o
     * resultado esperado. Os pacotes de visão e controle devem estar no mesmo
     * nível, devem ser nomeados com "view" e "control" sucessivamente e seus
     * nomes devem seguir o padrão CamelCase. Ex.:
     * projeto.modulo.view.Arquivo.fxml
     * projeto.modulo.control.ArquivoControl.java
     *
     * @param controller
     * @param title
     * @param style
     * @throws java.io.IOException
     */
    public void openFrame(final Class controller, String title, StageStyle style) throws IOException {
        openFrame(controller, title, style, Modality.NONE);
    }

    /**
     * Abre o Frame com um FXML dentro a partir do Class de seu controlador e
     * faz com que haja apenas uma instâncias de cada frame.
     *
     * Obs.: Deve-se obedecer o padrão de nomeclatura nos pacotes, classes de
     * controle e arquivos fxml de visão e controle para que este método tenha o
     * resultado esperado. Os pacotes de visão e controle devem estar no mesmo
     * nível, devem ser nomeados com "view" e "control" sucessivamente e seus
     * nomes devem seguir o padrão CamelCase. Ex.:
     * projeto.modulo.view.Arquivo.fxml
     * projeto.modulo.control.ArquivoControl.java
     *
     * @param controller
     * @param title
     * @param modality
     * @throws java.io.IOException
     */
    public void openFrame(final Class controller, String title, Modality modality) throws IOException {
        openFrame(controller, title, StageStyle.DECORATED, modality);
    }

    /**
     * Abre o Frame com um FXML dentro a partir do Class de seu controlador e
     * faz com que haja apenas uma instâncias de cada frame.
     *
     * Obs.: Deve-se obedecer o padrão de nomeclatura nos pacotes, classes de
     * controle e arquivos fxml de visão e controle para que este método tenha o
     * resultado esperado. Os pacotes de visão e controle devem estar no mesmo
     * nível, devem ser nomeados com "view" e "control" sucessivamente e seus
     * nomes devem seguir o padrão CamelCase. Ex.:
     * projeto.modulo.view.Arquivo.fxml
     * projeto.modulo.control.ArquivoControl.java
     *
     * @param controller
     * @param title
     * @param style
     * @param modality
     * @throws java.io.IOException
     */
    public void openFrame(final Class controller, String title, StageStyle style, Modality modality) throws IOException {
        final Stage stage;

        if (!containerStages.containsKey(controller)) {
            
            //Verifica se já foi instânciado um FXMLLoader vinculado ao Class do controlador.
            URL urlView = getUrlView(controller);
            //Cria um objeto FXMLLoader
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(urlView);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            //Pega o objeto criado a partir do FXML
            Parent fxml = (Parent) fxmlLoader.load(urlView.openStream());
            //Adiciona à memória o objeto FXMLLoader criado
            containerFXMLLoader.put(fxmlLoader.getController().getClass(), fxmlLoader);

            Scene scene = new Scene(fxml);

            //Se o estilo do Stage for transparente
            if (style == StageStyle.TRANSPARENT) {
                //Deixa o fundo do cenário transparente
                scene.setFill(null);
            }

            stage = new Stage(style);
            //Faz com que o novo Stage não bloquei os demais
            stage.initModality(modality);
            //Especifica que o novo Stage é filho do Stage prinicipal
            stage.initOwner(stageMain);
            //Seta o título do Frame
            stage.setTitle(title);
            stage.setScene(scene);
            //Se tiver um favicon
            if (favicon != null) {
                stage.getIcons().add(favicon);
            }
            stage.setResizable(false);

            ///////// Correção de Erro do JavaFX ao definir "setResizable(false)" /////////
            if (stage.getScene().getRoot() instanceof Pane) {
                Pane anchor = (Pane) stage.getScene().getRoot();
                stage.setWidth(anchor.getPrefWidth() + 6);
                stage.setHeight(anchor.getPrefHeight() + 28);
            }
            ///////// Correção de Erro do JavaFX ao definir "setResizable(false)" /////////

            stage.setFullScreen(false);
            stage.show();

            //Armazena a instância do novo Stage
            stages.add(stage);
            containerStages.put(controller, stage);

            EventHandler<WindowEvent> handler = new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    //stages.remove((Stage) event.getSource());
                    stages.remove(containerStages.get(controller));
                    containerStages.remove(controller);
                    containerFXMLLoader.remove(controller);
                }
            };

            //Remove as instâncias dos Frames antes de fechar-lo
            stage.setOnHiding(handler);
            //Adidiciona um KeyEvent
            addKeyEvent(stage);
            //Atribui ações ao ganhar foco
            stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    //Se ganhou foco
                    if (newValue) {
                        //Se existe mais de um Stage
                        if (stages.size() > 1) {
                            //Remove-o do início e coloca-o no final da lista
                            stages.remove(stage);
                            stages.add(stage);
                        }
                    }
                }
            });
            
        } else {
            //Pega o Stage solicitado
            stage = (Stage) containerStages.get(controller);
            //Se estiver minimizado, maximiza-o e coloca-o na frente de todos
            stage.setIconified(false);
            stage.toFront();
        }
    }
    
    
    public void openFrame(final FXMLInitializable controller, String title, StageStyle style, Modality modality) throws IOException {
        final Stage stage;

        if (!containerStages.containsKey(controller)) {
            
            //Inicializar FXMLInitializable
            controller.init();
            
            //Adiciona à memória o objeto FXMLLoader criado
            containerFXMLLoader.put(controller.getClass(), new FXMLLoader(controller.getFxml().toURL()));

            Scene scene = new Scene(controller.getRoot());

            //Se o estilo do Stage for transparente
            if (style == StageStyle.TRANSPARENT) {
                //Deixa o fundo do cenário transparente
                scene.setFill(null);
            }

            stage = new Stage(style);
            //Faz com que o novo Stage não bloquei os demais
            stage.initModality(modality);
            //Especifica que o novo Stage é filho do Stage prinicipal
            stage.initOwner(stageMain);
            //Seta o título do Frame
            stage.setTitle(title);
            stage.setScene(scene);
            //Se tiver um favicon
            if (favicon != null) {
                stage.getIcons().add(favicon);
            }
            stage.setResizable(false);

            ///////// Correção de Erro do JavaFX ao definir "setResizable(false)" /////////
            if (stage.getScene().getRoot() instanceof Pane) {
                Pane anchor = (Pane) stage.getScene().getRoot();
                stage.setWidth(anchor.getPrefWidth() + 6);
                stage.setHeight(anchor.getPrefHeight() + 28);
            }
            ///////// Correção de Erro do JavaFX ao definir "setResizable(false)" /////////

            stage.setFullScreen(false);
            stage.show();

            //Armazena a instância do novo Stage
            stages.add(stage);
            containerStages.put(controller.getClass(), stage);

            EventHandler<WindowEvent> handler = new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    //stages.remove((Stage) event.getSource());
                    stages.remove(containerStages.get(controller));
                    containerStages.remove(controller);
                    containerFXMLLoader.remove(controller);
                }
            };

            //Remove as instâncias dos Frames antes de fechar-lo
            stage.setOnHiding(handler);
            //Adidiciona um KeyEvent
            addKeyEvent(stage);
            //Atribui ações ao ganhar foco
            stage.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    //Se ganhou foco
                    if (newValue) {
                        //Se existe mais de um Stage
                        if (stages.size() > 1) {
                            //Remove-o do início e coloca-o no final da lista
                            stages.remove(stage);
                            stages.add(stage);
                        }
                    }
                }
            });
            
        } else {
            //Pega o Stage solicitado
            stage = (Stage) containerStages.get(controller);
            //Se estiver minimizado, maximiza-o e coloca-o na frente de todos
            stage.setIconified(false);
            stage.toFront();
        }
    }

    /**
     * Retorna um objeto URL a partir do Class do controlador.
     *
     * Obs.: Deve-se obedecer o padrão de nomeclatura nos pacotes, classes de
     * controle e arquivos fxml de visão e controle para que este método tenha o
     * resultado esperado. Os pacotes de visão e controle devem estar no mesmo
     * nível, devem ser nomeados com "view" e "control" sucessivamente e seus
     * nomes devem seguir o padrão CamelCase. Ex.:
     * projeto.modulo.view.Arquivo.fxml
     * projeto.modulo.control.ArquivoControl.java
     *
     * @param Class controller
     * @return URL
     */
    private URL getUrlView(Class controller) {
        //Monta o path do controlador
        String pathControl = "/" + (controller.getName()).replaceAll("\\.", "/");
        //Substitui o nome da camada e coloca a extensão .fxml no path do controlador
        return getClass().getResource(pathControl.replace("control", "view").replace("Control", ".fxml"));
    }

    /**
     * Acopla o script que adiciona o KeyEvent aos Stages.
     *
     * @param Stage stage
     */
    private void addKeyEvent(Stage stage) {
        //Executado quando se aperta um botão do teclado.
        stage.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                //Se a tecla CTRL estiver pressionada e a tecla TAB for pressionada
                if (event.isControlDown() && event.getCode() == KeyCode.TAB) {
                    //Se existirem Stages abertos
                    if (!stages.isEmpty()) {
                        //Pega o primeiro da lista
                        Stage primeiro = stages.get(0);
                        //Se estiver minimizado, maximiza-o e coloca-o na frente de todos
                        primeiro.setIconified(false);
                        primeiro.toFront();
                        //Remove-o do início e coloca-o no final da lista
                        stages.remove(primeiro);
                        stages.add(primeiro);
                    }
                }
            }
        });
    }

    /**
     * Retorna a instância do stage solicitado ou null caso não seja encontrado.
     *
     * @param controller
     * @return Stage
     */
    public Stage getStage(Class controller) {
        return containerStages.get(controller);
    }

    /**
     * Retorna a instância do controlador solicitado ou null caso não seja
     * encontrado.
     *
     * @param controller
     * @return Initializable
     */
    public Initializable getController(Class controller) {
        Object control = containerFXMLLoader.get(controller);
        // Verifica se realmente existe um FXMLLoader vinculado ao Class do controlador.
        return (control != null) ? (Initializable) containerFXMLLoader.get(controller).getController() : null;
    }

    /**
     * Fecha todas os Frames abertos, exceto o principal.
     */
    public void closeAll() {
        List<Stage> stagesClose = new ArrayList(containerStages.values());
        //Vasculha dos Stages e fecha-os
        for (Stage stageClose : stagesClose) {
            stageClose.close();
        }

        //Apaga todos os registros
        stages.clear();
        containerStages.clear();
        containerFXMLLoader.clear();
    }
}
