package com.example.movielibrary.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Pair;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class MultiChoiceDialogFragment extends DialogFragment {

    public interface onMultiChoiceListener {
        void onPositiveButtonClicked(Pair<String, ArrayList<String>> data);
        void onNegativeButtonClicked();
    }

    onMultiChoiceListener listener;

    String[] list = null;
    String[] listValues = null;

    EditText editText;
    String dialogTitle;
    String dictionaryKey;

    public MultiChoiceDialogFragment(String[] list,  String[] listValues, EditText editText, String dialogTitle, String dictionaryKey){
        this.list = list;
        this.listValues = listValues;
        this.editText = editText;
        this.dialogTitle = dialogTitle;
        this.dictionaryKey = dictionaryKey;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (onMultiChoiceListener) context;
        } catch (Exception e) {
            throw new ClassCastException();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final ArrayList<String> selectedItems = new ArrayList<>();
        final ArrayList<String> selectedItemsDisplay = new ArrayList<>();


        builder.setTitle(dialogTitle)
                .setMultiChoiceItems(list, null, (dialogInterface, i, b) -> {
                    if(b){
                        selectedItems.add(listValues[i]);
                        selectedItemsDisplay.add(list[i]);
                    }else{
                        selectedItems.remove(listValues[i]);
                        selectedItemsDisplay.remove(list[i]);
                    }
                })
                .setPositiveButton("Ok", (dialogInterface, i) -> {
                    if (!selectedItemsDisplay.isEmpty()) {
                        editText.setText(String.join(",", selectedItemsDisplay));
                    } else {
                        editText.setText("");
                    }
                    Pair<String, ArrayList<String>> data = new Pair<>(dictionaryKey, selectedItems);

                    listener.onPositiveButtonClicked(data);
                })
                .setNegativeButton("Cancel", (dialogInterface, i) -> {});

        return builder.create();
    }
}
