#include <gtkmm/application.h>
#include <gtkmm/window.h>
#include <gtkmm/box.h>
#include <gtkmm/label.h>
#include <gtkmm/button.h>



class MyWindow : public Gtk::Window {
public:
    MyWindow();
private:
    void on_button_clicked();

    Gtk::Box m_box;           // Box to arrange widgets vertically
    Gtk::Label m_label;       // Label to display text
    Gtk::Button m_button;     // Button to trigger an action
};

