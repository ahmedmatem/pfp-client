package com.example.android.pfpnotes.ui;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.android.pfpnotes.R;

import java.lang.reflect.Field;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NumberPickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NumberPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NumberPickerFragment extends Fragment implements NumberPicker.OnValueChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private NumberPicker mNumberPickerMeter;
    private NumberPicker mNumberPickerDecimeter;
    private NumberPicker mNumberPickerCentimeter;

    private int mNumber;
    private int mCentimeter = 0;
    private int mDecimeter = 0;
    private int mMeter = 0;

    public NumberPickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NumberPickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NumberPickerFragment newInstance(String param1, String param2) {
        NumberPickerFragment fragment = new NumberPickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.fragment_number_picker, container, false);

        mNumberPickerMeter =
                (NumberPicker) rootView.findViewById(R.id.number_picker_meter);
        mNumberPickerDecimeter =
                (NumberPicker) rootView.findViewById(R.id.number_picker_decimeter);
        mNumberPickerCentimeter =
                (NumberPicker) rootView.findViewById(R.id.number_picker_centimeter);

        // set onValueChangedListener on pickers
        mNumberPickerMeter.setOnValueChangedListener(this);
        mNumberPickerDecimeter.setOnValueChangedListener(this);
        mNumberPickerCentimeter.setOnValueChangedListener(this);

        setNumberPickers();

        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: try to change number pickers selected text color
    private void setNumberPickers() {
        // meters
        mNumberPickerMeter.setMinValue(0);
        mNumberPickerMeter.setMaxValue(10);
        mNumberPickerMeter.setWrapSelectorWheel(true);

        // decimeters
        mNumberPickerDecimeter.setMinValue(0);
        mNumberPickerDecimeter.setMaxValue(9);
        mNumberPickerDecimeter.setWrapSelectorWheel(true);

        // centimeters
        mNumberPickerCentimeter.setMinValue(0);
        mNumberPickerCentimeter.setMaxValue(9);
        mNumberPickerCentimeter.setWrapSelectorWheel(true);
    }

    // TODO: Delete next method
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onNumberPickerValueChanged();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        int pickerId = picker.getId();
        switch (pickerId){
            case R.id.number_picker_meter:
                mMeter = newVal;
                break;
            case R.id.number_picker_decimeter:
                mDecimeter = newVal;
                break;
            case R.id.number_picker_centimeter:
                mCentimeter = newVal;
                break;
        }
        // calculate new number
        mNumber = mCentimeter + 10 * mDecimeter + 100 * mMeter;

        // call activity contains the fragment to validate menu
        if(mListener != null) {
            mListener.onNumberPickerValueChanged();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onNumberPickerValueChanged();
    }

    public int getNumber() {
        return mNumber;
    }
}
