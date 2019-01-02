package by.bsuir.golda.moneymanager;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private Context appContext;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void useAppContext() {
        assertEquals("by.bsuir.golda.moneymanager", appContext.getPackageName());
    }

    @Test
    public void useAppName() {
        assertEquals("MoneyManager", appContext.getString(R.string.app_name));
    }

    @Test
    public void testAppNameIsNotShop() {
        assertNotEquals("Shop", appContext.getString(R.string.app_name));
    }

    @Test
    public void useHeaderSubtitle(){
        assertEquals("leha.golda@gmail.com", appContext.getString(R.string.nav_header_subtitle));
    }
}
