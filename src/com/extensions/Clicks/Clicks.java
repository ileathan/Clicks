package com.extensions.clicks;
import android.view.View;
import android.view.View.OnClickListener;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.AndroidViewComponent;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.EventDispatcher;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@DesignerComponent(version = 1,
        category = ComponentCategory.EXTENSION,
        description = "Double click, triple click, etc...",
        nonVisible = true,
        iconName = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAIAAACQkWg2AAAABGdBTUEAALGPC/xhBQAAACBjSFJNA" +
                "AB6JgAAgIQAAPoAAACA6AAAdTAAAOpgAAA6mAAAF3CculE8AAAABmJLR0QA/wD/AP+gvaeTAAAAB3RJTUUH5AoVEg0LUaic4wAAArpJ" +
                "REFUKM8tzs1Pk3ccAPDvy+95nvLQIpVqsYVCiwtKOIhGR5zDmI0lHpYsGaIxnnbQXTx41+y+v8I/YYcl6mWXZfMlsmVQDRRKS0VWKNC" +
                "uT5+2z9vvu8N2/xw+WPq5bBmW4zqlysZmtVRv/N3rd6MwQiJ7wD5zOpMfy0+OF9IjaT8MPK+vnE77xcpvr/56eXjcCMLAUAqRtNYAAC" +
                "BrpVVEGh4avnB+7sb8F2OjY9w6ar5Zfe15nlKGZVogBFosw2ImJmUqk4h6Xm+jsrGy9kYEOJvK2jGbiAAgCsN8IXl2KtNqNUVYiwYUQ" +
                "CCkmBXzw6BYWuPp8WkBAQARGErwd7cfLM4veX5nu7qp2EIERAi1CAghGYZB/12REAFMZSpIKB60Y0NKiWGQbZsCSIhEjESIqLTWkUjU" +
                "6wPS4ZH30/OnyROp9+WiZQ2ABIcNJxQmojAIRCSKIp7Ozzz68Un8ZPLmvW9LxU2P0LSlkM22O22Oj1xbXgIEOx6f/Xx+fHrqs6+/Uje" +
                "+uXlp/lLuk3xieBhiFkZh7fc/UpT4tb5y4drs8oO75YVPXbdLhIi4U1xXZnzwh4ePD2q7c19ev7pwef3tn83ax2Aw0ev7B3t75fWtrf" +
                "Wt1OlT26vvup6/8uIXXLy4iACIqEgmcyONIzefT+1U96OIvTCamMi0Gsf7TVd0RMzKUMowDEAAgQGLg4iF1Wh6MplMVSt71d2GIkRmU" +
                "7FGBSJaayIi0YIAqVNxK2Zk05n63n5udGZqfMayqOMG9qDJDP9TIHLcNiJqLSDGmXRmMpf6xz0W0WwFySGbmTwvACRGdlzH6Tm8tHir" +
                "3vg4EBMmdNqdZtMJQ9ipVbpueHTcAq0DPwwjrx/0c9n8/Tvf49azyvtysXawKuSPxCf6XkdLtFv/UN7eVmz6gd/1u4Vc4fLswty5K0T" +
                "0L6iCUmvDBCI1AAAAJXRFWHRkYXRlOmNyZWF0ZQAyMDIwLTEwLTIxVDE4OjEzOjExLTA0OjAwzfx1WgAAACV0RVh0ZGF0ZTptb2RpZn" +
                "kAMjAyMC0xMC0yMVQxODoxMzoxMS0wNDowMLyhzeYAAAAASUVORK5CYII=")

@SimpleObject(external = true)
public class Clicks extends AndroidNonvisibleComponent {
    public Clicks(ComponentContainer container) { super(container.$form()); }
    @SimpleFunction(description = "Sets up the object with a list of amounts to receive double, triple, etc, clicks. " +
            "The component is any button, the amount is the amount of clicks until WasClicked event is fired, repeatable " +
            "is true/false whether WasClicked will keep firing. resetTime resets the internal click counter if the " +
            "user hasn't reclicked it is an integer value representing millieseconds, values 200-400 here with " +
            "amount set to 2 would simulate a double click")
    public void SetListOfClicks(final AndroidViewComponent component, final List<Integer> amount, final boolean repeatable, final int resetTime) {
        myClicks clicks = new myClicks(this, component, amount,  repeatable, resetTime);
    }
    @SimpleFunction(description = "Sets up the object with one amount to receive double, triple, etc, clicks. The component" +
            "is any button, the amount is the amount of clicks until WasClicked event is fired, repeatable is " +
            "true/false whether WasClicked will keep firing. resetTime resets the internal click counter if the " +
            "user hasn't reclicked it is an integer value representing millieseconds, values 200-400 here with " +
            "amount set to 2 would simulate a double click")
    public void SetClicks(final AndroidViewComponent component, final int amount, final boolean repeatable, final int resetTime) {
        final List<Integer> tmpList = Collections.singletonList(amount);
        SetListOfClicks(component, tmpList, repeatable, resetTime);
    }
    @SimpleFunction(description = "Unregisters the object from this extension.")
    public void UnsetClicks(AndroidViewComponent component) {
        component.getView().setOnClickListener(null);
    }
    @SimpleEvent(description = "Fired when an error occurs.")
    public void ErrorEvent(String error) {
        EventDispatcher.dispatchEvent(this, "ErrorEvent", error);
    }
    @SimpleEvent(description = "Is fired when the object was (double, triple, etc) clicked.")
    public void WasClicked(AndroidViewComponent component, int amount, boolean repeatable, int resetTime) {
        EventDispatcher.dispatchEvent(this, "WasClicked", component, amount, repeatable, resetTime);
    }
}
class myClicks {
    private Clicks clicksHandle;
    private AndroidViewComponent component;
    private int resetTime;
    private int currentClicks;
    private boolean repeatable;
    private boolean timerIsRunning;
    private Object[] listAmountArray;
    private static Timer timer;
    myClicks(final Clicks clicksHandle, final AndroidViewComponent component, final List<Integer> amount, final boolean repeatable, final int resetTime){
        try {
            this.clicksHandle = clicksHandle;
            this.currentClicks = 0;
            this.component = component;
            this.repeatable = repeatable;
            this.resetTime = resetTime;
            this.timerIsRunning = false;
            this.listAmountArray = amount.toArray();

            OnClickListener viewClick = new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(timerIsRunning) { timer.cancel(); timerIsRunning = false; }
                    ++currentClicks;
                    if (resetTime > 0) {
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                // The total internal clicker is reset here but if the
                                // User clicks before it goes off it is canceled.
                                clickChecker();
                                currentClicks = 0;
                                timerIsRunning = false;
                            }
                        };
                        timer = new Timer();
                        timerIsRunning = true;
                        timer.schedule(timerTask, resetTime);
                    } else {
                        clickChecker();
                    }
                }
            };
            // Getting the reference to the View of the parameter component and setting its onClickListener
            component.getView().setOnClickListener(viewClick);
        } catch (Exception e) {
            clicksHandle.ErrorEvent(e.getMessage());
        }
    }
    private void clickChecker() {
        for (Object o : listAmountArray) {
            int tmpAmnt = Integer.parseInt(o.toString());
            if (resetTime > 0 && currentClicks == tmpAmnt || resetTime == 0 && currentClicks % tmpAmnt == 0) {
                clicksHandle.WasClicked(component, tmpAmnt, repeatable, resetTime);
                if (!repeatable) {
                    component.getView().setOnClickListener(null);
                }
            }
        }
    }
}
