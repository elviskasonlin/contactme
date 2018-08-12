package com.sp.contactme;

import android.database.Cursor;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ezvcard.Ezvcard;
import ezvcard.VCard;

public class ProfileHolder {
    public TextView profileName, formattedName;
    public Button btnShare, btnEdit;

    ProfileHolder(View row) {
        profileName = (TextView) row.findViewById(R.id.txtProfileName);
        formattedName = (TextView) row.findViewById(R.id.txtFormattedName);
        btnShare = (Button) row.findViewById(R.id.btnShareProfile);
        btnEdit = (Button) row.findViewById(R.id.btnEditProfile);
    }

    void populateFrom(Cursor c, VCardStorageHelper helper) {
        profileName.setText(helper.getProfile(c));

        String data = helper.getData(c);
        VCard vCard = Ezvcard.parse(data).first();
        String name = vCard.getFormattedName().getValue();

        formattedName.setText(name);
    }

}
