
package com.kii.apis;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ListActivity;
import android.content.Intent;
import android.content.res.TypedArray;

import com.kii.apis.R;
import com.kii.apis.abtest.ABTestsActivity;
import com.kii.apis.analytics.EventAnalyticsActivity;
import com.kii.apis.analytics.FlexAnalyticsActivity;
import com.kii.apis.extension.ServerExtensionActivity;
import com.kii.apis.file.DownloadingFilesActivity;
import com.kii.apis.file.PublishingFilesActivity;
import com.kii.apis.file.UploadingFilesActivity;
import com.kii.apis.geolocation.AddPOIActivity;
import com.kii.apis.geolocation.QueryByGeoBoxActivity;
import com.kii.apis.geolocation.QueryByGeoDistanceActivity;
import com.kii.apis.group.GroupManagementActivity;
import com.kii.apis.object.NotesList;
import com.kii.apis.push.PushActivity;
import com.kii.apis.user.LogoutDeleteActivity;
import com.kii.apis.user.SignInActivity;
import com.kii.apis.user.SignUpActivity;
import com.kii.apis.user.SimpleSignUpInActivity;
import com.kii.apis.user.SocialNetworkIntegrationActivity;
import com.kii.apis.user.UserAttributesActivity;
import com.kii.cloud.analytics.KiiAnalytics;
import com.kii.cloud.analytics.KiiEvent;
import com.kii.cloud.storage.Kii;

import java.io.IOException;

/**
 * This activity shows the categories.
 */

public class MainActivity extends ListActivity {
    static {
        Kii.initialize(Constants.KII_APP_ID, Constants.KII_APP_KEY, Constants.KII_SITE);
    }

    public static final String EXTRA_CATE = "cate_id";
    public static final String EXTRA_TITLE = "title";

    int cate = 0;
    String[] items = null;
    int[] item_ids;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KiiAnalytics.initialize(this, Constants.KII_APP_ID, Constants.KII_APP_KEY,
                Constants.KII_ANALYTICS_SITE);
        cate = getIntent().getIntExtra(EXTRA_CATE, 0);
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        if (!TextUtils.isEmpty(title)) {
            setTitle(title);
        }
        TypedArray id_array = null;
        switch (cate) {
            case R.id.cate_user_management:
                items = getResources().getStringArray(R.array.cate_user_management);
                id_array = getResources().obtainTypedArray(R.array.cate_user_management_ids);
                break;
            case R.id.cate_geolocation:
                items = getResources().getStringArray(R.array.cate_geolocation);
                id_array = getResources().obtainTypedArray(R.array.cate_geolocation_ids);
                break;
            case R.id.cate_file_storage:
                items = getResources().getStringArray(R.array.cate_file_storage);
                id_array = getResources().obtainTypedArray(R.array.cate_file_storage_ids);
                break;
            case R.id.cate_analytics:
                items = getResources().getStringArray(R.array.cate_analytics);
                id_array = getResources().obtainTypedArray(R.array.cate_analytics_ids);
                break;
            case 0:
            default:
                items = getResources().getStringArray(R.array.kii_components);
                id_array = getResources().obtainTypedArray(R.array.kii_components_ids);
                break;
        }
        item_ids = new int[id_array.length()];
        for (int i = 0; i < item_ids.length; i++) {
            item_ids[i] = id_array.getResourceId(i, 0);
        }
        id_array.recycle();
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, items);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long lid) {
        super.onListItemClick(l, v, position, lid);
        int id = item_ids[position];
        String title = items[position];
        Intent intent = null;
        switch (id) {
            case R.id.cate_file_storage:
            case R.id.cate_geolocation:
            case R.id.cate_analytics:
            case R.id.cate_user_management: {
                intent = new Intent(this, MainActivity.class);
                intent.putExtra(EXTRA_CATE, id);
                intent.putExtra(EXTRA_TITLE, title);
            }
                break;
            case R.id.cate_group_management: 
                intent = new Intent(this, GroupManagementActivity.class);
                break;
            case R.id.cate_push: 
                intent = new Intent(this, PushActivity.class);
                break;
            case R.id.cate_server_extension:
                intent = new Intent(this, ServerExtensionActivity.class);
                break;
            case R.id.cate_object_storage: {
                KiiEvent event = KiiAnalytics.event("ClickOnNotepad");
                try {
                    event.push();
                } catch (IOException e) {
                }
                intent = new Intent(this, NotesList.class);
            }
                break;
            case R.id.cate_abtests: {
                KiiEvent event = KiiAnalytics.event("ClickOnABTest");
                try {
                    event.push();
                } catch (IOException e) {
                }
                intent = new Intent(this, ABTestsActivity.class);
            }
                break;
            case R.id.sign_up:
                intent = new Intent(this, SignUpActivity.class);
                break;
            case R.id.simple_sign_up_in:
                intent = new Intent(this, SimpleSignUpInActivity.class);
                break;
            case R.id.delete_user:
                intent = new Intent(this, LogoutDeleteActivity.class);
                break;
            case R.id.flex_analytics:
                intent = new Intent(this, FlexAnalyticsActivity.class);
                break;
            case R.id.event_analytics:
                intent = new Intent(this, EventAnalyticsActivity.class);
                break;
            case R.id.sign_in:
                intent = new Intent(this, SignInActivity.class);
                break;
            case R.id.user_attributes:
                intent = new Intent(this, UserAttributesActivity.class);
                break;
            case R.id.add_geopoint:
                intent = new Intent(this, AddPOIActivity.class);
                break;
            case R.id.query_pois_by_geobox:
                intent = new Intent(this, QueryByGeoBoxActivity.class);
                break;
            case R.id.query_pois_by_geodistance:
                intent = new Intent(this, QueryByGeoDistanceActivity.class);
                break;
            case R.id.uploading_files:
                intent = new Intent(this, UploadingFilesActivity.class);
                break;
            case R.id.downloading_files:
                intent = new Intent(this, DownloadingFilesActivity.class);
                break;
            case R.id.publishing_files:
                intent = new Intent(this, PublishingFilesActivity.class);
                break;
            case R.id.social_network_integration:
                intent = new Intent(this, SocialNetworkIntegrationActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

}