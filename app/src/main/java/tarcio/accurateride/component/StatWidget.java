package tarcio.accurateride.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tarcio.accurateride.R;

public class StatWidget extends RelativeLayout {

    private TextView txtStatLabel;
    private TextView txtStatValue;
    private TextView txtStatUnit;

    public StatWidget(Context context) {
        super(context);
        init(context, null);
    }

    public StatWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View.inflate(context, R.layout.stat, this);

        txtStatLabel = (TextView) findViewById(R.id.statLabel);
        txtStatValue = (TextView) findViewById(R.id.statValue);
        txtStatUnit = (TextView) findViewById(R.id.statUnit);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StatWidget, 0, 0);

            String labelText = "";
            String unitText = "";
            float fontSize = 0f;

            try {
                labelText = a.getString(R.styleable.StatWidget_statLabel);
                unitText = a.getString(R.styleable.StatWidget_statUnit);
                fontSize = a.getDimension(R.styleable.StatWidget_statValueSize, 48f);
            } catch (Exception e) {
                Log.e("StatWidget", "Could not load attributes.");
            } finally {
                a.recycle();
            }

            setLabelText(labelText);
            setUnitText(unitText);
            setValueTextSize(fontSize);
        }
    }

    private void setValueTextSize(float fontSize) {
        this.txtStatValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
    }

    private void setUnitText(String unitText) {
        this.txtStatUnit.setText(unitText);
    }

    private void setLabelText(String labelText) {
        this.txtStatLabel.setText(labelText);
    }

    private void setValueText(String valueText) {
        this.txtStatValue.setText(valueText);
    }

}
