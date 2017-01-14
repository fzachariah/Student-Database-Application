import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.control.MenuBar
import scalafx.Includes._
import scalafx.scene.control.Menu
import scalafx.scene.control.MenuItem
import javafx.scene.control.SeparatorMenuItem
import scalafx.scene.layout.BorderPane
import scalafx.scene.control.TabPane
import scalafx.event.ActionEvent
import scalafx.scene.control.Label
import scalafx.scene.control.TextArea

import scalafx.scene.control.TextField
import scalafx.scene.layout.VBox
import scalafx.geometry.Insets
import scalafx.scene.layout.Priority
import scalafx.scene.layout.HBox
import java.io.PrintWriter
import java.io.FileWriter
import java.util.Scanner
import java.io.File
import scala.util.control._

class StudentData extends Exception {

  /**
   * class variables used in storing the basic information.
   */

  var name: String = "";
  var univName: String = "";
  var age: String = "";
  var studentId: String = "";
  var deptName: String = "";
  var courses = List[String]();
  var location: String = ""

  /**
   * method used to read input from the console.
   * @param
   * @return
   * all class variables are assigned with values by reading input from console
   */

  def readInput(id: String, univeName: String, ageValue: String, studentName: String, depname: String, courseList: List[String]) {
    val locations = Map("cse" -> "woodward", "eee" -> "EPIC", "it" -> "chhs")
    var myVal: String = "";
    name = studentName
    univName = univeName
    studentId = id
    age = (ageValue)

    deptName = depname
    if (locations.contains(deptName.toLowerCase())) {
      location = locations(deptName.toLowerCase())
    } else {
      location = "OTHER"
    }

    for (i <- 0 until 4) {

      courses = courses :+ courseList(i)
    }
    val pw = new PrintWriter(new FileWriter("hello.txt", true))
    pw.println("Student Id: " + studentId);
    pw.println("Student Name: " + name);
    pw.println("University Name: " + univName);
    pw.println("Age: " + age);
    pw.println("Department: " + deptName);
    pw.println("Building: " + location);
    for (i <- 0 until courses.length) {
      pw.println("Course Number" + (i + 1) + ":" + courses(i));
    }
    pw.println();
    pw.close();
  }

  def searchId(search: String) {

    val fileIn = new Scanner(new File("hello.txt"));
    val loop = new Breaks;
    loop.breakable {
      while (fileIn.hasNextLine()) {
        var temp = fileIn.nextLine()
        if (temp.contains(search) && temp.contains("Student Id: ")) {
          studentId = temp;
          name = (fileIn.nextLine())
          univName = fileIn.nextLine()
          age = fileIn.nextLine()
          deptName = fileIn.nextLine()
          location = fileIn.nextLine()
          var i = 0;
          while (i < 4) {
            courses = courses :+ fileIn.nextLine()
            i = i + 1
          }
          loop.break;
        }
      }
    }

  }

}

object DrawingMain extends JFXApp {

  stage = new JFXApp.PrimaryStage {
    title = "Student Data Application"

    scene = new Scene(700, 600) {
      val menubar = new MenuBar
      val editMenu = new Menu("Edit Details")
      val addItem = new MenuItem("Add Student")
      val viewItem = new MenuItem("View Student")
      val exitItem = new MenuItem("Exit")
      editMenu.items = List(addItem, viewItem, new SeparatorMenuItem, exitItem)
      menubar.menus = List(editMenu)
      val rootpane = new BorderPane
      rootpane.top = menubar
      root = rootpane
      addItem.onAction = (event: ActionEvent) => {

        val idLabel = new Label {
          text = "Student ID:"
          wrapText = true
        }
        val idTtext = new TextField {

        }
        val hBox1 = new HBox {
          spacing = 50
          content = List(idLabel, idTtext)
        }

        val nameLabel = new Label {
          text = "Student Name:"
          wrapText = true
        }
        val nameText = new TextField {

        }
        val hBox2 = new HBox {
          spacing = 30
          content = List(nameLabel, nameText)
        }

        val ageLabel = new Label {
          text = "Age:"
          wrapText = true
        }
        val ageTtext = new TextField {

        }
        val hBox3 = new HBox {
          spacing = 85
          content = List(ageLabel, ageTtext)
        }

        val univNameLabel = new Label {
          text = "University Name:"
          wrapText = true
        }
        val univNameTtext = new TextField {

        }
        val hBox4 = new HBox {
          spacing = 20
          content = List(univNameLabel, univNameTtext)
        }

        val deptLabel = new Label {
          text = "Department Name:"
          wrapText = true
        }
        val deptTtext = new TextField {

        }
        val hBox5 = new HBox {
          spacing = 9
          content = List(deptLabel, deptTtext)
        }

        val subLabel = new Label {
          text = "Subjects:"
          wrapText = true
        }
        val subOneText = new TextField {

        }
        val subTwoText = new TextField {

        }
        val subThreeText = new TextField {

        }
        val subFourText = new TextField {

        }

        val hBox6 = new HBox {
          spacing = 4
          content = List(subLabel, subOneText, subTwoText, subThreeText, subFourText)
        }

        val button = new Button("Submit Details") {

        }
        val Vbox = new VBox {
          vgrow = Priority.ALWAYS
          hgrow = Priority.ALWAYS
          content = List(hBox1, hBox2, hBox3, hBox4, hBox5, hBox6, button)
          button.onAction = (e: ActionEvent) => {
            val studentData = new StudentData();
            var coursesList = List[String]();
            coursesList = coursesList :+ subOneText.getText()
            coursesList = coursesList :+ subTwoText.getText()
            coursesList = coursesList :+ subThreeText.getText()
            coursesList = coursesList :+ subFourText.getText()

            studentData.readInput(idTtext.getText(), univNameTtext.getText(), ageTtext.getText(), nameText.getText(), deptTtext.getText(), coursesList);
            idTtext.clear()
            univNameTtext.clear()
            ageTtext.clear()
            nameText.clear()
            deptTtext.clear()
            subOneText.clear()
            subTwoText.clear()
            subThreeText.clear()
            subFourText.clear()
          }

        }
        rootpane.center = Vbox
      }

      viewItem.onAction = (event: ActionEvent) => {
        val labelSearch = new Label {
          text = "Student ID:"
          wrapText = true
        }
        val textFieldSearch = new TextField {
          maxWidth = Double.MaxValue
          maxHeight = Double.MaxValue

        }
        val hBox7 = new HBox {
          content = List(labelSearch, textFieldSearch)
        }
        val buttonSearch = new Button("Search Details") {

        }

        val VboxNew = new VBox {
          content = List(hBox7, buttonSearch)
          buttonSearch.onAction = (e: ActionEvent) => {
            if (textFieldSearch.getText().equals("")) {

            } else {
              val studentData = new StudentData();
              studentData.searchId(textFieldSearch.getText())
              val idLabel = new Label {
                text = studentData.studentId
                wrapText = true
              }
              val nameLabel = new Label {
                text = studentData.name
                wrapText = true
              }
              val ageLabel = new Label {
                text = studentData.age
                wrapText = true
              }
              val univNameLabel = new Label {
                text = studentData.univName
                wrapText = true
              }
              val deptLabel = new Label {
                text = studentData.deptName
                wrapText = true
              }
              val locLabel = new Label {
                text = studentData.location
                wrapText = true
              }

              content = List(hBox7, buttonSearch, nameLabel, idLabel, ageLabel, univNameLabel, deptLabel, locLabel)
            }
          }

        }
        rootpane.center = VboxNew
      }
      exitItem.onAction = (event: ActionEvent) => {
        sys.exit(0)
      }

    }
  }

}