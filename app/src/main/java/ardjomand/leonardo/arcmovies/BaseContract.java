package ardjomand.leonardo.arcmovies;

/**
 * Created by unity on 25/02/18.
 */

public interface BaseContract {

    interface View<T> {

        void setPresenter(T presenter);

        void setLoading(boolean visibility);

        void showErrorMessage(String message);
    }
}
