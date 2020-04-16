package Error;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class TimePicker extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR);
        int minuts =calendar.get(Calendar.MINUTE);
        long mil =calendar.get(Calendar.SECOND);
        int mily =calendar.get(Calendar.SECOND);
        Log.e("hour",String.valueOf(hour));
        Log.e("minu",String.valueOf(minuts));
        Log.e("mil",String.valueOf(mil));
        Log.e("milly",String.valueOf(mily));


        return new TimePickerDialog(getActivity(), (TimePickerDialog.OnTimeSetListener) getActivity(), hour, minuts, DateFormat.is24HourFormat(getActivity()));



    }
}
