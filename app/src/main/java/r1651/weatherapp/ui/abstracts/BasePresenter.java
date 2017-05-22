package r1651.weatherapp.ui.abstracts;

public class BasePresenter<T extends BaseView> {

    public T mBaseView;

    public void setView(T view) {
        mBaseView = view;
    }

    public void dropView() {
        mBaseView = null;
    }

}
