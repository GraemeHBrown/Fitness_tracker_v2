package com.codeclan.example.fitnesstrackerapp.useractivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.codeclan.example.fitnesstrackerapp.R;
import com.codeclan.example.fitnesstrackerapp.db.AppDatabase;
import com.codeclan.example.fitnesstrackerapp.equipment.Equipment;
import com.codeclan.example.fitnesstrackerapp.user.User;

import java.util.List;

public class EquipmentSelectFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private AppDatabase db;

    private OnEquipmentSelectedListener mListener;

    private Equipment noEquipmentOption;

    public EquipmentSelectFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInMemoryDatabase(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_equipment_select, container, false);
        Spinner spinner = view.findViewById(R.id.equipment_select_spinner);
        spinner.setOnItemSelectedListener(this);
        List<Equipment> equipment = fetchEquipment();

        ArrayAdapter<Equipment> equipmentAdapter = new ArrayAdapter<Equipment>(getContext(), R.layout.support_simple_spinner_dropdown_item, equipment);

        equipmentAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(equipmentAdapter);
        int position = equipmentAdapter.getPosition(noEquipmentOption);
        Log.d("Position of default", String.valueOf(position));
        spinner.setSelection(position);
        return view;
    }

    private List<Equipment> fetchEquipment() {
        User appUser = db.userDao().getAll().get(0);
        noEquipmentOption = new Equipment();
        noEquipmentOption.setEquipmentModel("No equipment");
        List<Equipment> equipForUser = db.equipmentDao().findAllEquipmentForUser(appUser.getId());
        equipForUser.add(noEquipmentOption);
        return equipForUser;
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
