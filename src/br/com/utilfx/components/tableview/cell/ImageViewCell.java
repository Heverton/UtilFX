package br.com.utilfx.components.tableview.cell;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import br.com.utilfx.components.tableview.TableViewModel;
import static br.com.utilfx.components.tableview.TableViewModel.TipoTableView.DEFAULT;
import static br.com.utilfx.components.tableview.TableViewModel.TipoTableView.EDITCELL;

/**
 * Cria um TableCell que possue um objeto ImageView com a imagem do atributo do
 * tipo Image do objeto da linha.
 *
 * @author Heverton Cruz
 * @version 1.0
 *
 * @param <S>
 * @param <T>
 */
public class ImageViewCell<S, T> extends TableCellFX<S, T> {

    private final ImageView imageView;
    private final TableViewModel model;

    public ImageViewCell(final TableViewModel model) {
        this(model, true, false);
    }

    public ImageViewCell(final TableViewModel model, boolean isFunctioDefault) {
        this(model, isFunctioDefault, false);
    }

    private ImageViewCell(final TableViewModel model, boolean isFunctioDefault, boolean bs) {
        this.model = model;
        this.imageView = new ImageView();
        //Centraliza objeto ImageView na célula
        this.setAlignment(Pos.CENTER);
        //Exibe o objeto ImageView
        this.setGraphic(imageView);

        switch (model.getTipo()) {
            case DEFAULT:
                //Define como não editável
                imageView.setDisable(true);
                break;
            case EDITCELL:
                if (isFunctioDefault) {
                    this.imageView.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent t) {
                            requestFocus();
                        }
                    });

                    if (model.getOnKeyEventColumns() != null) {
                        this.imageView.addEventFilter(KeyEvent.KEY_PRESSED, model.getOnKeyEventColumns());
                    }

                    this.imageView.setOnKeyReleased(new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent t) {
                            if (t.getCode() == KeyCode.ENTER) {
                                model.cellFocusNext();
                            } else if (t.getCode() == KeyCode.ESCAPE) {
                                cancelEdit();
                            }
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();//Pega a posição atual da célula que possue o foco da seleção

        switch (model.getTipo()) {
            case DEFAULT:
                break;
            case EDITCELL:
                //avança para a proxima célula
                model.selectCelNext();
                break;
        }
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            //Oculta o texto da célula se houver
            setText(null);
            //Oculta o objeto ImageView
            setGraphic(null);
        } else {
            //Seta a imagem no ImageView
            imageView.setImage((Image) item);
            //Exibe o objeto ImageView
            setGraphic(imageView);
        }
    }

    /**
     * Transforma os valores de item nulos em vazio.
     *
     * @return String
     */
    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    /**
     * Retorna o objeto de edição.
     *
     * @return ImageView
     */
    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public Node getNode() {
        return imageView;
    }
}
