package com.tandev.musichub.fragment.profile;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.tandev.musichub.R;
import com.tandev.musichub.adapter.playlist.PlaylistAdapter;
import com.tandev.musichub.adapter.playlist.PlaylistUserAdapter;
import com.tandev.musichub.adapter.song.SongAllAdapter;
import com.tandev.musichub.helper.ui.Helper;
import com.tandev.musichub.model.chart.chart_home.Items;
import com.tandev.musichub.model.playlist.DataPlaylist;
import com.tandev.musichub.model.playlist.Playlist;
import com.tandev.musichub.sharedpreferences.SharedPreferencesManager;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    //tool bar
    private View tool_bar;
    private RelativeLayout relative_header;
    private TextView txt_title_toolbar;
    private ImageView img_back;

    private NestedScrollView nested_scroll;
    private RecyclerView rv_playlist;
    private RecyclerView rv_song;
    private ArrayList<DataPlaylist> dataPlaylists;
    private ArrayList<Items> itemsArrayList;
    private PlaylistUserAdapter playlistUserAdapter;
    private SongAllAdapter songAllAdapter;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferencesManager = new SharedPreferencesManager(requireContext());

        initViews(view);
        conFigViews();
        onClick();
        initAdapter();

        getData();
    }

    private void getData() {
        dataPlaylists = sharedPreferencesManager.restorePlaylistUser();
        playlistUserAdapter.setFilterList(dataPlaylists);

        itemsArrayList = sharedPreferencesManager.restoreSongArrayListFavorite();
        songAllAdapter.setFilterList(itemsArrayList);
    }

    private void initViews(View view) {
        tool_bar = view.findViewById(R.id.tool_bar);
        relative_header = tool_bar.findViewById(R.id.relative_header);
        img_back = tool_bar.findViewById(R.id.img_back);
        txt_title_toolbar = tool_bar.findViewById(R.id.txt_title_toolbar);

        nested_scroll = view.findViewById(R.id.nested_scroll);
        rv_playlist = view.findViewById(R.id.rv_playlist);
        rv_song = view.findViewById(R.id.rv_song);

    }

    private void conFigViews() {
        nested_scroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @SuppressLint({"ObsoleteSdkInt", "SetTextI18n"})
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY <= 200) {
                    txt_title_toolbar.setText("");
                    relative_header.setBackgroundResource(android.R.color.transparent);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        requireActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                        requireActivity().getWindow().setStatusBarColor(android.graphics.Color.TRANSPARENT);
                    }

                } else if (scrollY >= 300) {
                    txt_title_toolbar.setText("Thư viện của bạn");
                    relative_header.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.bg));
                    Helper.changeStatusBarColor(requireActivity(), R.color.bg);
                }
            }
        });
    }

    private void onClick() {
        img_back.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void initAdapter() {
        dataPlaylists = new ArrayList<>();
        itemsArrayList = new ArrayList<>();

        rv_playlist.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        playlistUserAdapter = new PlaylistUserAdapter(dataPlaylists, requireActivity(), requireContext());
        rv_playlist.setAdapter(playlistUserAdapter);

        rv_song.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        songAllAdapter = new SongAllAdapter(itemsArrayList, requireActivity(), requireContext());
        rv_song.setAdapter(songAllAdapter);

    }
}