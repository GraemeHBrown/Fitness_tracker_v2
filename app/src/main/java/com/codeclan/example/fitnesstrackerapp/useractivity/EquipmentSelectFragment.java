package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.viewmodel.EquipmentForUserViewModel;

import java.util.List;

public class EquipmentSelectFragment extends Fragment implements AdapterView.OnItemSelectedListener {


    private OnEquipmentSelectedListener mListener;
    private Equipment noEquipmentOption;

    private EquipmentForUserViewModel userEquipModel;

    public EquipmentSelectFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userEquipModel = ViewModelProviders.of(this).get(EquipmentForUserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipment_select, container, false);
        Spinner spinner = view.findViewById(R.id.equipment_select_spinner);
        spinner.setOnItemSelectedListener(this);

        userEquipModel.getLiveUserEquipment().observe(this, new Observer<List<Equipment>>() {
            @Override
            public void onChanged(@Nullable List<Equipment> equipmentList) {
                if (equipmentList != null) {
                    List<Equipment> listWithNoEquipmentOption = addNoEquipmentOptionToList(equipmentList);
                    ArrayAdapter<Equipment> equipmentAdapter = setUpEquipmentAdapter(listWithNoEquipmentOption);
                    spinner.setAdapter(equipmentAdapter);
                    int position = equipmentAdapter.getPosition(noEquipmentOption);
                    spinner.setSelection(position);
                }
            }
        });

        return view;
    }

    private ArrayAdapter<Equipment> setUpEquipmentAdapter(List<Equipment> listWithNoEquipmentOption) {
        ArrayAdapter<Equipment> equipmentAdapter = new ArrayAdapter<Equipment>(getContext(), R.layout.support_simple_spinner_dropdown_item, listWithNoEquipmentOption);
        equipmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        return equipmentAdapter;
    }

    private List<Equipment> addNoEquipmentOptionToList(List<Equipment> equipmentList) {
        noEquipmentOption = new Equipment();
        noEquipmentOption.setEquipmentModel("No equipment");
        equipmentList.add(noEquipmentOption);

        return equipmentList;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnEquipmentSelectedListener) {
            mListener = (OnEquipmentSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnEquipmentSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Equipment selectedEquipment = (Equipment) parent.getItemAtPosition(position);
        mListener.onEquipmentSelected(selectedEquipment);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public interface OnEquipmentSelectedListener {
        void onEquipmentSelected(Equipment equipment);
    }
}
