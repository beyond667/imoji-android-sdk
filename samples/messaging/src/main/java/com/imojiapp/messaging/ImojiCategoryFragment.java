package com.imojiapp.messaging;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.imoji.sdk.ApiTask;
import io.imoji.sdk.ImojiSDK;
import io.imoji.sdk.objects.Category;
import io.imoji.sdk.response.CategoriesResponse;

public class ImojiCategoryFragment extends Fragment {
    public static final String CLASSIFICATION_BUNDLE_ARG_KEY = "CLASSIFICATION_BUNDLE_ARG_KEY";
    private static final String LOG_TAG = ImojiCategoryFragment.class.getSimpleName();
    RecyclerView mCategoryGrid;

    ImojiCategoryRecyclerAdapter mCategoryAdapter;
    private String mClassification;
    private TextView mTitle;

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
        mCategoryGrid = (RecyclerView) v.findViewById(R.id.rv_imoji_grid);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mTitle = (TextView) view.findViewById(R.id.tv_title);
        if (Category.Classification.Trending.name().equals(mClassification)) {
            mTitle.setText("TRENDING");
        } else if (Category.Classification.Generic.name().equals(mClassification)) {
            mTitle.setText("REACTIONS");
            mCategoryGrid.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    Category imojiCategory = mCategoryAdapter.getItemAt(position);
                    ImojiSearchFragment fragment = ImojiSearchFragment.newInstance(imojiCategory.getIdentifier(), false);
                    getChildFragmentManager().beginTransaction().add(R.id.category_imojis_container, fragment).addToBackStack(null).commitAllowingStateLoss();

                }
            }));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        mCategoryGrid.setLayoutManager(manager);
        mCategoryAdapter = new ImojiCategoryRecyclerAdapter(getActivity());
        mCategoryGrid.setAdapter(mCategoryAdapter);
        loadImojiCategories(mClassification);
    }

    private void loadImojiCategories(String classification) {
        Category.Classification c  = Category.Classification.Trending;
        if (Category.Classification.Generic.name().equals(classification)) {
            c = Category.Classification.Generic;
        }

        ImojiSDK.getInstance().createSession(getContext()).getImojiCategories(c).executeAsyncTask(new ApiTask.WrappedAsyncTask<CategoriesResponse>() {
            @Override
            protected void onPostExecute(CategoriesResponse categoriesResponse) {
                if (isResumed()) {
                    mCategoryAdapter.setImojiCategories(categoriesResponse.getCategories());
                }
            }
        });
    }
}
