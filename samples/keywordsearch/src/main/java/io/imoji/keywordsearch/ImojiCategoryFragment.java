package io.imoji.keywordsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import io.imoji.sdk.ApiTask;
import io.imoji.sdk.ImojiSDK;
import io.imoji.sdk.Session;
import io.imoji.sdk.objects.Category;
import io.imoji.sdk.response.CategoriesResponse;

public class ImojiCategoryFragment extends Fragment {
    public static final String CLASSIFICATION_BUNDLE_ARG_KEY = "CLASSIFICATION_BUNDLE_ARG_KEY";
    private static final String LOG_TAG = ImojiCategoryFragment.class.getSimpleName();
    GridView mCategoryGrid;

    ImojiCategoryAdapter mCategoryAdapter;
    private String mClassification;

    public static ImojiCategoryFragment newInstance(String classification) {
        ImojiCategoryFragment f = new ImojiCategoryFragment();

        Bundle args = new Bundle();
        args.putString(CLASSIFICATION_BUNDLE_ARG_KEY, classification);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassification = getArguments().getString(CLASSIFICATION_BUNDLE_ARG_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_imoji_category, container, false);
        mCategoryGrid = (GridView) v.findViewById(R.id.gv_imoji_grid);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        loadImojiCategories(mClassification);

        mCategoryGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isResumed()) {
                    Category category = (Category) parent.getItemAtPosition(position);
                    ImojiSearchFragment f = ImojiSearchFragment.newInstance(category.getIdentifier());
                    getFragmentManager().beginTransaction().replace(R.id.tab_container, f).commit();
                }
            }
        });
    }


    private void loadImojiCategories(String classificationStr) {
        Category.Classification classification = Category.Classification.Generic;
        if ("trending".equalsIgnoreCase(classificationStr)) {
            classification = Category.Classification.Trending;
        }

        Session session = ImojiSDK.getInstance().createSession(getContext());
        session.getImojiCategories(classification).executeAsyncTask(new ApiTask.WrappedAsyncTask<CategoriesResponse>() {
            @Override
            protected void onPostExecute(CategoriesResponse categoriesResponse) {
                if (isResumed()) {
                    mCategoryAdapter = new ImojiCategoryAdapter(getActivity(), R.layout.category_item_layout, categoriesResponse.getCategories());
                    mCategoryGrid.setAdapter(mCategoryAdapter);
                }
            }
        });
    }

}
