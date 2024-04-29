package android.example.gomscheduler;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageButton;

public class HeadButton extends AppCompatImageButton {

    public HeadButton(Context context) {
        super(context);
    }

    public HeadButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getPerson() {
        return this.getTag().toString(); // Replace with your desired person's name
    }
}