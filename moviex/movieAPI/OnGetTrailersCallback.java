package sg.edu.tp.moviex.movieAPI;

import java.util.List;

import sg.edu.tp.moviex.Model.Trailer;

public interface OnGetTrailersCallback {
    String onSuccess(List<Trailer> trailers);

    void onError();
}
