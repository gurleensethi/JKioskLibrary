package app.com.thetechnocafe.jkiosklibrary;

import java.util.ArrayList;
import java.util.List;

import app.com.thetechnocafe.jkiosklibrary.Contracts.KioskContract;

/**
 * Created by gurleensethi on 12/06/17.
 */

public class KioskArray {
    private List<KioskContract> kioskContracts;

    public KioskArray() {
        kioskContracts = new ArrayList<>();
    }

    /*
    * Remove callbacks from all the kiosk contract object added to the kioskContracts list
    * */
    public void removeAllCallbacks() {
        for (KioskContract kioskContract : kioskContracts) {
            kioskContract.removeCallback();
        }
    }

    //Add the kiosk contract object to the list
    public void add(KioskContract kioskContract) {
        kioskContracts.add(kioskContract);
    }

    //Remove kiosk contract object from the list
    public void remove(KioskContract kioskContract) {
        kioskContracts.remove(kioskContract);
    }
}
