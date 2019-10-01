package com.rockbite.tools.talos.editor.widgets.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;
import com.rockbite.tools.talos.TalosMain;

public class EditableLabel extends Table {

    Table labelTabel;
    Table inputTable;

    Label label;
    TextField textField;

    public EditableLabel(String text, Skin skin) {
        super(skin);

        Stack stack = new Stack();

        labelTabel = new Table();
        inputTable = new Table();

        stack.add(labelTabel);
        stack.add(inputTable);

        add(stack).grow();

        label = new Label(text, getSkin(), "default");
        labelTabel.add(label);
        labelTabel.add().expandX();


        textField = new TextField("", getSkin(), "no-bg");
        inputTable.add(textField);
        inputTable.add().expandX();

        addListener(new ClickListener() {

            long clickTime = 0;

            @Override
            public void clicked(InputEvent event, float x, float y) {
                long time = TimeUtils.millis();

                if(time - clickTime < 200) {
                    setEditMode();
                }

                clickTime = time;

                super.clicked(event, x, y);
            }
        });

        textField.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if(keycode == Input.Keys.ENTER) {
                    setStaticMode();
                }

                return super.keyDown(event, keycode);
            }
        });

        pack();
    }

    public void setEditMode() {
        labelTabel.setVisible(false);
        inputTable.setVisible(true);
        textField.setText(label.getText().toString());
        textField.selectAll();
        getStage().setKeyboardFocus(textField);
        TalosMain.Instance().NodeStage().getStage().unfocusAll();
    }

    public void setStaticMode() {
        labelTabel.setVisible(true);
        inputTable.setVisible(false);

        label.setText(textField.getText());
        textField.clearSelection();
    }
}
