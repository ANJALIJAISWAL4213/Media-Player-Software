package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class mainController implements Initializable {
	@FXML
	private AnchorPane root;
	@FXML
	private Slider timeslider;
	@FXML
	private Slider vslider;

	@FXML
	private Button playbtn;
	@FXML
	private Button forbtn;
	@FXML
	private Button backbtn;
	@FXML
	private Button volup;
	@FXML
	private Button voldown;

	@FXML
	private Label medianame;
	@FXML
	private MenuItem incspeed;
	@FXML
	private MenuItem decspeed;
	@FXML
	private MenuItem about;
	@FXML
	private MenuItem lite;
	@FXML
	private MenuItem dark;

	@FXML
	private MediaView mv;
	@FXML
	private MediaPlayer mp;
	@FXML
	private Media me;
	@FXML
	private ImageView img;

	public int theme = 1;

	@FXML
	public void opensong(ActionEvent event) {
		try {
			FileChooser fc = new FileChooser();
			fc.getExtensionFilters().addAll(new ExtensionFilter("Media File", "*mp3", "*mp4"));
			File file = fc.showOpenDialog(null);
			if (file != null) {
				System.out.println("Imported Successfully");
			} else {
				System.out.println("Import Failed");
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Error");
				alert.setContentText("You must import a file to play");
				alert.showAndWait();
			}

			String name = new String();
			name = file.getName();
			medianame.setText(name);

			if (mp != null) {
				mp.dispose();
			}

			me = new Media(file.toURI().toURL().toString());
			mp = new MediaPlayer(me);
			mv.setMediaPlayer(mp);

			mp.setOnReady(() -> {
				mp.play();
				vslider.setValue(50);
				timeslider.setMin(0);
				timeslider.setMax(mp.getMedia().getDuration().toMinutes());
				timeslider.setValue(0);
				if (theme == 1) {
					playbtn.setGraphic(new ImageView("Icons/pause.png"));
				} else {
					playbtn.setGraphic(new ImageView("Icons/pause dark.png"));
				}
			});
			vslider.valueProperty().addListener(new InvalidationListener() {

				@Override
				public void invalidated(Observable arg0) {
					mp.setVolume(vslider.getValue() / 100);

				}
			});
			mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {

				@Override
				public void changed(ObservableValue<? extends Duration> observable, Duration oldValue,
						Duration newValue) {
					Duration d = mp.getCurrentTime();
					timeslider.setValue(d.toMinutes());
				}

			});

			timeslider.valueProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					if (timeslider.isPressed()) {

						double value = timeslider.getValue();
						mp.seek(new Duration(value * 60000));
					}

				}
			});

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exitApp(ActionEvent event) {
		System.exit(0);
	}

	public void play(ActionEvent event) {
		if (this.mp == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setHeaderText("Error");
			alert.setContentText("You must import a file to play");
			alert.showAndWait();
		}
		MediaPlayer.Status status = mp.getStatus();
		if (status == Status.PLAYING) {
			mp.pause();
			if (theme == 1) {
				playbtn.setGraphic(new ImageView("Icons/play.png"));
			} else {
				playbtn.setGraphic(new ImageView("Icons/play dark.png"));
			}

		} else {
			mp.play();
			if (theme == 1) {
				playbtn.setGraphic(new ImageView("Icons/pause.png"));
			} else {
				playbtn.setGraphic(new ImageView("Icons/pause dark.png"));
			}

		}

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		img.setImage(new Image ("Icons/Media.png"));
		vslider.setValue(50);
		if (theme == 1) {
			playbtn.setGraphic(new ImageView("Icons/play.png"));
			forbtn.setGraphic(new ImageView("Icons/forward.png"));
			backbtn.setGraphic(new ImageView("Icons/backward.png"));
			volup.setGraphic(new ImageView("Icons/up.png"));
			voldown.setGraphic(new ImageView("Icons/down.png"));
		} else {
			playbtn.setGraphic(new ImageView("Icons/play dark.png"));
			forbtn.setGraphic(new ImageView("Icons/forward dark.png"));
			backbtn.setGraphic(new ImageView("Icons/backward dark.png"));
			volup.setGraphic(new ImageView("Icons/up dark.png"));
			voldown.setGraphic(new ImageView("Icons/down dark.png"));
		}

	}

	public void fast(ActionEvent event) {
		if (mp.getRate() == 1) {
			mp.setRate(2);
			incspeed.setText("Back to normal");
		} else {
			mp.setRate(1);
			incspeed.setText("Increase Speed");
			decspeed.setText("Decrease Speed");
		}
	}

	public void slow(ActionEvent event) {
		if (mp.getRate() == 1) {
			mp.setRate(0.5);
			decspeed.setText("Back to normal");
		} else {
			mp.setRate(1);
			decspeed.setText("Decrease Speed");
			incspeed.setText("Increase Speed");

		}
	}

	public void gtstart(ActionEvent event) {
		mp.seek(mp.getStartTime());
		mp.play();
		if (theme == 1) {
			playbtn.setGraphic(new ImageView("Icons/pause.png"));
		} else {
			playbtn.setGraphic(new ImageView("Icons/pause dark.png"));
		}
	}

	public void gtlast(ActionEvent event) {
		mp.seek(mp.getTotalDuration());
		mp.pause();
		if (theme == 1) {
			playbtn.setGraphic(new ImageView("Icons/play.png"));
		} else {
			playbtn.setGraphic(new ImageView("Icons/play dark.png"));
		}

	}

	public void volumeUp(ActionEvent event) {
		vslider.setValue(100);

	}

	public void volumeDown(ActionEvent event) {
		vslider.setValue(0);

	}

	public void forward(ActionEvent event) {
		double d = mp.getCurrentTime().toSeconds();
		d = d + 10;
		mp.seek(new Duration(d * 1000));

	}

	public void backward(ActionEvent event) {
		double d = mp.getCurrentTime().toSeconds();
		d = d - 10;
		mp.seek(new Duration(d * 1000));
	}

	public void playmenu(ActionEvent event) {
		mp.play();
		if (theme == 1) {
			playbtn.setGraphic(new ImageView("Icons/pause.png"));
		} else {
			playbtn.setGraphic(new ImageView("Icons/pause dark.png"));
		}
	}

	public void pausemenu(ActionEvent event) {
		mp.pause();
		if (theme == 1) {
			playbtn.setGraphic(new ImageView("Icons/play.png"));
		} else {
			playbtn.setGraphic(new ImageView("Icons/play dark.png"));
		}

	}

	public void about(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Copyright");
		alert.setTitle("About this project");
		
		alert.setContentText(
				"This project was created by Shiam Ahmed Sizan and AKM Arifuzzaman \n © 2021 Sizan and Arif. All rights reserved.");
		alert.showAndWait();
	}

	public void themeLite(ActionEvent event) {
		if (theme == 2) {
			root.getStylesheets().clear();
			root.getStylesheets().add(getClass().getResource("lite.css").toExternalForm());
			theme = 1;
			forbtn.setGraphic(new ImageView("Icons/forward.png"));
			backbtn.setGraphic(new ImageView("Icons/backward.png"));
			volup.setGraphic(new ImageView("Icons/up.png"));
			voldown.setGraphic(new ImageView("Icons/down.png"));
		}

		if (mp != null) {
			MediaPlayer.Status status = mp.getStatus();
			if (status == Status.PLAYING) {
				playbtn.setGraphic(new ImageView("Icons/pause.png"));
			} else {
				playbtn.setGraphic(new ImageView("Icons/play.png"));
			}
		} else {
			playbtn.setGraphic(new ImageView("Icons/play.png"));
		}

	}

	public void themeDark(ActionEvent event) {
		if (theme == 1) {
			root.getStylesheets().clear();
			root.getStylesheets().add(getClass().getResource("dark.css").toExternalForm());
			theme = 2;
			forbtn.setGraphic(new ImageView("Icons/forward dark.png"));
			backbtn.setGraphic(new ImageView("Icons/backward dark.png"));
			volup.setGraphic(new ImageView("Icons/up dark.png"));
			voldown.setGraphic(new ImageView("Icons/down dark.png"));
		}
		if (mp != null) {
			MediaPlayer.Status status = mp.getStatus();
			if (status == Status.PLAYING) {
				playbtn.setGraphic(new ImageView("Icons/pause dark.png"));
			} else {
				playbtn.setGraphic(new ImageView("Icons/play dark.png"));
			}
		} else {
			playbtn.setGraphic(new ImageView("Icons/play dark.png"));
		}

	}
}
