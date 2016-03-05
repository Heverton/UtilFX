package br.com.utilfx.components.tableview.cellfactory;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.util.Callback;
import br.com.utilfx.components.tableview.TableViewModel;
import br.com.utilfx.components.tableview.cell.ImageViewCell;
import br.com.utilfx.components.util.CellReflection;

/**
 * Cria um objeto para controlar a célula das colunas que possuirem o objeto
 * ImageView.
 *
 * @author Heverton Cruz, Heverton Cruz
 * @version 1.0
 */
public class ImageViewCellFactory implements Callback<TableColumn, TableCell> {

    private final TableViewModel tableViewModel;
    private final String projetionImage;
    private final boolean isFunctioDefault;

    public ImageViewCellFactory(TableViewModel tableViewModel, String projetion, boolean isFunctioDefault) {
        this.projetionImage = projetion;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = isFunctioDefault;
    }

    public ImageViewCellFactory(TableViewModel tableViewModel, String projetion) {
        this.projetionImage = projetion;
        this.tableViewModel = tableViewModel;
        this.isFunctioDefault = true;
    }

    @Override
    public ImageViewCell call(TableColumn tcif) {
        final ImageViewCell cell = new ImageViewCell(tableViewModel, isFunctioDefault);
        //Aplica a atualização do pojo ao selecionar o objeto
        cell.getImageView().imageProperty().addListener(new ChangeListener<Image>() {
            @Override
            public void changed(ObservableValue ov, Image oldValue, Image newValue) {
                //Seta a alteração no pojo
                CellReflection.setValuePojo(
                        cell.getTableView().getItems().get(cell.getIndex()), projetionImage, new Object[]{newValue});
            }
        });

        return cell;
    }
}
