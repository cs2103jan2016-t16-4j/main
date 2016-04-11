# generated
###### Fantasktic\bin\application\gui\CalendarItem.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.effect.DropShadow?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.shape.Rectangle?>
<?import javafx.scene.text.Font?>

<fx:root maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="178.0" prefWidth="274.0" style="-fx-border-insets: 0; -fx-border-color: black; -fx-background-radius: 0; -fx-border-width: 0;" type="AnchorPane" xmlns="http://javafx.com/javafx/8.0.65" xmlns:fx="http://javafx.com/fxml/1">
	<children>
      <Rectangle arcHeight="5.0" arcWidth="5.0" fill="WHITE" height="178.0" stroke="TRANSPARENT" strokeType="INSIDE" strokeWidth="0.0" style="-fx-background-radius: 0;" width="274.0">
         <effect>
            <DropShadow color="#000000b9" offsetY="5.0" />
         </effect>
      </Rectangle>
		<Rectangle fx:id="rectangle" arcWidth="5.0" fill="#ff1f1f" height="41.0" layoutY="142.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" style="-fx-border-width: 0; -fx-background-radius: 0;" width="274.0" />
		<Label fx:id="taskName" alignment="TOP_LEFT" layoutX="14.0" layoutY="14.0" prefHeight="41.0" prefWidth="244.0" textFill="#757474">
         <font>
            <Font size="22.0" />
         </font></Label>
		<Label fx:id="date" layoutX="14.0" layoutY="62.0" prefHeight="27.0" prefWidth="257.0" textFill="#757474" />
		<Label fx:id="locationLabel" layoutX="14.0" layoutY="107.0" prefHeight="27.0" prefWidth="244.0" textFill="#757474" />
      <Label fx:id="indexLabel" alignment="CENTER" layoutX="112.0" layoutY="149.0" prefHeight="27.0" prefWidth="53.0" text="1" textAlignment="CENTER" textFill="WHITE">
         <font>
            <Font name="System Bold" size="18.0" />
         </font>
      </Label>
	</children>
	<padding>
		<Insets right="50.0" />
	</padding>
</fx:root>
```
###### Fantasktic\bin\application\gui\DateObject.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.shape.Line?>
<?import javafx.scene.text.Font?>

<fx:root fx:id="dateObject" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="199.0" prefWidth="1053.0" type="HBox" xmlns="http://javafx.com/javafx/8.0.65" xmlns:fx="http://javafx.com/fxml/1">
	<children>
      <AnchorPane prefHeight="200.0" prefWidth="200.0">
         <children>
      		<ListView fx:id="listViewItem" layoutX="50.0" orientation="HORIZONTAL" prefHeight="199.0" prefWidth="1012.0" style="-fx-background-insets: 0; -fx-border-color: transparent;" styleClass="calendarItem" />
            <Line endX="1050.0" endY="198.0" startX="15.0" startY="198.0" stroke="#575454" strokeWidth="0.5" />
            <Label fx:id="dateLabel" alignment="CENTER" contentDisplay="CENTER" layoutX="-69.0" layoutY="82.0" prefHeight="35.0" prefWidth="190.0" rotate="-90.0" text="Label" textAlignment="CENTER" textFill="#757474">
               <font>
                  <Font size="24.0" />
               </font>
            </Label>
         </children>
      </AnchorPane>
	</children>
</fx:root>
```
###### Fantasktic\bin\application\gui\ListItem.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.effect.DropShadow?>
<?import javafx.scene.layout.HBox?>

<fx:root prefHeight="40.0" style="-fx-border-width: 0;" type="HBox"
	xmlns="http://javafx.com/javafx/8.0.65" xmlns:fx="http://javafx.com/fxml/1">
	<children>
		<Label fx:id="listNumber" alignment="CENTER" prefHeight="40.0"
			prefWidth="40.0" style="-fx-background-color: #A5D6A7; -fx-text-fill: white;"
			text="1" textFill="WHITE" />
		<Label fx:id="taskName" maxHeight="-Infinity" maxWidth="-Infinity"
			prefHeight="40.0" prefWidth="450.0" style="-fx-background-color: white;"
			text="HELLO" textFill="#757474">
			<HBox.margin>
				<Insets />
			</HBox.margin>
			<padding>
				<Insets left="5.0" right="5.0" />
			</padding>
		</Label>
		<Label fx:id="date" alignment="CENTER_RIGHT" maxHeight="-Infinity"
			maxWidth="-Infinity" prefHeight="40.0" prefWidth="310.0"
			style="-fx-background-color: white;" textFill="#868282">
			<padding>
				<Insets left="5.0" right="15.0" />
			</padding>
			<HBox.margin>
				<Insets />
			</HBox.margin>
		</Label>
		<Label fx:id="taskLocation" maxHeight="-Infinity" maxWidth="-Infinity"
			prefHeight="40.0" prefWidth="250.0" style="-fx-background-color: white;"
			textFill="#868282">
			<padding>
				<Insets left="15.0" right="5.0" />
			</padding>
		</Label>
	</children>
	<effect>
		<DropShadow color="#0000007b" offsetY="2.0" />
	</effect>
</fx:root>
```
###### Fantasktic\bin\application\gui\MainPage.fxml
``` fxml

<?import java.lang.*?>
<?import javafx.geometry.*?>
<?import javafx.scene.*?>
<?import javafx.scene.chart.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.effect.*?>
<?import javafx.scene.layout.*?>
<?import javafx.scene.shape.*?>
<?import javafx.scene.text.*?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.Cursor?>
<?import javafx.scene.chart.PieChart?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.effect.DropShadow?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.shape.Rectangle?>
<?import javafx.scene.text.Font?>

<fx:root maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="750.0" prefWidth="1107.0" style="-fx-background-color: white;" type="AnchorPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
	<children>
		<Rectangle arcWidth="5.0" fill="#00acc1" height="172.0" smooth="false" stroke="#fcf8f800" strokeType="INSIDE" strokeWidth="0.0" style="-fx-border-color: transparent;" width="1122.0">
			<effect>
				<DropShadow color="#000000bc" height="30.0" offsetY="5.0" radius="12.25" />
			</effect>
		</Rectangle>
		<Label fx:id="feedbackLabel" alignment="TOP_LEFT" layoutX="135.0" layoutY="107.0" prefHeight="52.0" prefWidth="953.0" text="Feedback Bar" textFill="WHITE">
			<padding>
				<Insets left="12.0" />
			</padding>
			<font>
				<Font name="System Bold" size="18.0" />
			</font>
		</Label>
		<TextField fx:id="textInputArea" layoutX="20.0" layoutY="14.0" prefHeight="59.0" prefWidth="1066.0" promptText="What would you like us to do for you?" style="-fx-background-color: white; -fx-background-radius: 0;">
			<font>
				<Font size="22.0" />
			</font>
			<effect>
				<DropShadow color="#00000094" offsetY="2.0" />
			</effect>
			<cursor>
				<Cursor fx:constant="TEXT" />
			</cursor>
		</TextField>
		<Label fx:id="helpLabel" alignment="TOP_LEFT" layoutX="85.0" layoutY="81.0" prefHeight="29.0" prefWidth="1003.0" text="Help Bar" textFill="WHITE">
			<font>
				<Font name="System Bold" size="18.0" />
			</font>
			<padding>
				<Insets left="12.0" />
			</padding>
		</Label>
		<Label alignment="TOP_LEFT" layoutX="20.0" layoutY="107.0" prefHeight="52.0" prefWidth="122.0" text="Feedback :" textFill="WHITE">
			<font>
				<Font name="System Bold" size="18.0" />
			</font>
			<padding>
				<Insets left="12.0" />
			</padding>
		</Label>
		<Label alignment="TOP_LEFT" layoutX="20.0" layoutY="81.0" prefHeight="52.0" prefWidth="122.0" text="Help :" textFill="WHITE">
			<font>
				<Font name="System Bold" size="18.0" />
			</font>
			<padding>
				<Insets left="12.0" />
			</padding>
		</Label>
		<StackPane fx:id="stackPane" layoutX="23.0" layoutY="184.0" prefHeight="555.0" prefWidth="1066.0" style="-fx-background-insets: 0;">
			<children>
				<ListView fx:id="displayList" prefHeight="501.0" prefWidth="1066.0" style="-fx-background-insets: 0;" styleClass="displayList" />
				<ListView fx:id="calendarList" prefHeight="447.0" prefWidth="1066.0" style="-fx-background-insets: 0;" />
			</children>
		</StackPane>
		<AnchorPane fx:id="hiddenMenu" layoutX="-335.0" layoutY="215.0" prefHeight="448.0" prefWidth="328.0" style="-fx-background-color: white;">
			<children>
				<VBox layoutX="64.0" layoutY="21.0" prefHeight="90.0" prefWidth="200.0">
					<children>
						<Label fx:id="Summary" alignment="CENTER" contentDisplay="CENTER" minWidth="-Infinity" prefHeight="27.0" prefWidth="200.0" style="-fx-underline: true; -fx-font-weight: bold;" text="SUMMARY" textFill="#616060">
							<font>
								<Font size="24.0" />
							</font>
						</Label>
						<Label alignment="CENTER" contentDisplay="CENTER" maxHeight="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="10.0" prefWidth="200.0" style="-fx-underline: true; -fx-font-weight: bold;" textFill="#616060">
							<font>
								<Font size="10.0" />
							</font>
						</Label>
						<HBox prefHeight="0.0" prefWidth="200.0">
							<children>
								<Label prefHeight="0.0" prefWidth="176.0" text="OVERDUE TASKS:" textFill="#6b6a6a" />
								<Label fx:id="overdueLabel" alignment="CENTER_RIGHT" prefHeight="27.0" prefWidth="23.0" text="0" textFill="#6b6a6a" />
							</children>
						</HBox>
						<HBox prefHeight="0.0" prefWidth="200.0">
							<children>
								<Label prefHeight="0.0" prefWidth="177.0" text="COMPLETED TASKS:" textFill="#6b6a6a" />
								<Label fx:id="completedLabel" alignment="CENTER_RIGHT" prefHeight="27.0" prefWidth="22.0" text="0" textFill="#6b6a6a" />
							</children>
						</HBox>
						<HBox prefHeight="0.0" prefWidth="200.0">
							<children>
								<Label prefHeight="0.0" prefWidth="177.0" text="REMAINING TASKS:" textFill="#6b6a6a" />
								<Label fx:id="remainingLabel" alignment="CENTER_RIGHT" prefHeight="27.0" prefWidth="22.0" text="0" textFill="#6b6a6a" />
							</children>
						</HBox>
					</children>
				</VBox>
				<PieChart fx:id="pieChart" layoutX="1.0" layoutY="139.0" prefHeight="259.0" prefWidth="326.0" />
			</children>
			<effect>
				<DropShadow color="#000000bf" offsetX="2.0" offsetY="2.0" />
			</effect>
		</AnchorPane>
	</children>
	<effect>
		<DropShadow />
	</effect>
</fx:root>
```
###### Fantasktic\src\application\gui\CalendarItem.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.effect.DropShadow?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.shape.Rectangle?>
<?import javafx.scene.text.Font?>

<fx:root maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="178.0" prefWidth="274.0" style="-fx-border-insets: 0; -fx-border-color: black; -fx-background-radius: 0; -fx-border-width: 0;" type="AnchorPane" xmlns="http://javafx.com/javafx/8.0.65" xmlns:fx="http://javafx.com/fxml/1">
	<children>
      <Rectangle arcHeight="5.0" arcWidth="5.0" fill="WHITE" height="178.0" stroke="TRANSPARENT" strokeType="INSIDE" strokeWidth="0.0" style="-fx-background-radius: 0;" width="274.0">
         <effect>
            <DropShadow color="#000000b9" offsetY="5.0" />
         </effect>
      </Rectangle>
		<Rectangle fx:id="rectangle" arcWidth="5.0" fill="#ff1f1f" height="41.0" layoutY="142.0" stroke="BLACK" strokeType="INSIDE" strokeWidth="0.0" style="-fx-border-width: 0; -fx-background-radius: 0;" width="274.0" />
		<Label fx:id="taskName" alignment="TOP_LEFT" layoutX="14.0" layoutY="14.0" prefHeight="41.0" prefWidth="244.0" textFill="#757474">
         <font>
            <Font size="22.0" />
         </font></Label>
		<Label fx:id="date" layoutX="14.0" layoutY="62.0" prefHeight="27.0" prefWidth="257.0" textFill="#757474" />
		<Label fx:id="locationLabel" layoutX="14.0" layoutY="107.0" prefHeight="27.0" prefWidth="244.0" textFill="#757474" />
      <Label fx:id="indexLabel" alignment="CENTER" layoutX="112.0" layoutY="149.0" prefHeight="27.0" prefWidth="53.0" text="1" textAlignment="CENTER" textFill="WHITE">
         <font>
            <Font name="System Bold" size="18.0" />
         </font>
      </Label>
	</children>
	<padding>
		<Insets right="50.0" />
	</padding>
</fx:root>
```
###### Fantasktic\src\application\gui\DateObject.fxml
``` fxml

<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.shape.Line?>
<?import javafx.scene.text.Font?>

<fx:root fx:id="dateObject" maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="199.0" prefWidth="1053.0" type="HBox" xmlns="http://javafx.com/javafx/8.0.65" xmlns:fx="http://javafx.com/fxml/1">
	<children>
      <AnchorPane prefHeight="200.0" prefWidth="200.0">
         <children>
      		<ListView fx:id="listViewItem" layoutX="50.0" orientation="HORIZONTAL" prefHeight="199.0" prefWidth="1012.0" style="-fx-background-insets: 0; -fx-border-color: transparent;" styleClass="calendarItem" />
            <Line endX="1050.0" endY="198.0" startX="15.0" startY="198.0" stroke="#575454" strokeWidth="0.5" />
            <Label fx:id="dateLabel" alignment="CENTER" contentDisplay="CENTER" layoutX="-69.0" layoutY="82.0" prefHeight="35.0" prefWidth="190.0" rotate="-90.0" text="Label" textAlignment="CENTER" textFill="#757474">
               <font>
                  <Font size="24.0" />
               </font>
            </Label>
         </children>
      </AnchorPane>
	</children>
</fx:root>
```
###### Fantasktic\src\application\gui\ListItem.fxml
``` fxml

<?import javafx.geometry.Insets?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.effect.DropShadow?>
<?import javafx.scene.layout.HBox?>

<fx:root prefHeight="40.0" style="-fx-border-width: 0;" type="HBox"
	xmlns="http://javafx.com/javafx/8.0.65" xmlns:fx="http://javafx.com/fxml/1">
	<children>
		<Label fx:id="listNumber" alignment="CENTER" prefHeight="40.0"
			prefWidth="40.0" style="-fx-background-color: #A5D6A7; -fx-text-fill: white;"
			text="1" textFill="WHITE" />
		<Label fx:id="taskName" maxHeight="-Infinity" maxWidth="-Infinity"
			prefHeight="40.0" prefWidth="450.0" style="-fx-background-color: white;"
			text="HELLO" textFill="#757474">
			<HBox.margin>
				<Insets />
			</HBox.margin>
			<padding>
				<Insets left="5.0" right="5.0" />
			</padding>
		</Label>
		<Label fx:id="date" alignment="CENTER_RIGHT" maxHeight="-Infinity"
			maxWidth="-Infinity" prefHeight="40.0" prefWidth="310.0"
			style="-fx-background-color: white;" textFill="#868282">
			<padding>
				<Insets left="5.0" right="15.0" />
			</padding>
			<HBox.margin>
				<Insets />
			</HBox.margin>
		</Label>
		<Label fx:id="taskLocation" maxHeight="-Infinity" maxWidth="-Infinity"
			prefHeight="40.0" prefWidth="250.0" style="-fx-background-color: white;"
			textFill="#868282">
			<padding>
				<Insets left="15.0" right="5.0" />
			</padding>
		</Label>
	</children>
	<effect>
		<DropShadow color="#0000007b" offsetY="2.0" />
	</effect>
</fx:root>
```
###### Fantasktic\src\application\gui\MainPage.fxml
``` fxml

<?import java.lang.*?>
<?import javafx.geometry.*?>
<?import javafx.scene.*?>
<?import javafx.scene.chart.*?>
<?import javafx.scene.control.*?>
<?import javafx.scene.effect.*?>
<?import javafx.scene.layout.*?>
<?import javafx.scene.shape.*?>
<?import javafx.scene.text.*?>
<?import javafx.geometry.Insets?>
<?import javafx.scene.Cursor?>
<?import javafx.scene.chart.PieChart?>
<?import javafx.scene.control.Label?>
<?import javafx.scene.control.ListView?>
<?import javafx.scene.control.TextField?>
<?import javafx.scene.effect.DropShadow?>
<?import javafx.scene.layout.AnchorPane?>
<?import javafx.scene.layout.HBox?>
<?import javafx.scene.layout.StackPane?>
<?import javafx.scene.layout.VBox?>
<?import javafx.scene.shape.Rectangle?>
<?import javafx.scene.text.Font?>

<fx:root maxHeight="-Infinity" maxWidth="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="750.0" prefWidth="1107.0" style="-fx-background-color: white;" type="AnchorPane" xmlns="http://javafx.com/javafx/8" xmlns:fx="http://javafx.com/fxml/1">
	<children>
		<Rectangle arcWidth="5.0" fill="#00acc1" height="172.0" smooth="false" stroke="#fcf8f800" strokeType="INSIDE" strokeWidth="0.0" style="-fx-border-color: transparent;" width="1122.0">
			<effect>
				<DropShadow color="#000000bc" height="30.0" offsetY="5.0" radius="12.25" />
			</effect>
		</Rectangle>
		<Label fx:id="feedbackLabel" alignment="TOP_LEFT" layoutX="135.0" layoutY="107.0" prefHeight="52.0" prefWidth="953.0" text="Feedback Bar" textFill="WHITE">
			<padding>
				<Insets left="12.0" />
			</padding>
			<font>
				<Font name="System Bold" size="18.0" />
			</font>
		</Label>
		<TextField fx:id="textInputArea" layoutX="20.0" layoutY="14.0" prefHeight="59.0" prefWidth="1066.0" promptText="What would you like us to do for you?" style="-fx-background-color: white; -fx-background-radius: 0;">
			<font>
				<Font size="22.0" />
			</font>
			<effect>
				<DropShadow color="#00000094" offsetY="2.0" />
			</effect>
			<cursor>
				<Cursor fx:constant="TEXT" />
			</cursor>
		</TextField>
		<Label fx:id="helpLabel" alignment="TOP_LEFT" layoutX="85.0" layoutY="81.0" prefHeight="29.0" prefWidth="1003.0" text="Help Bar" textFill="WHITE">
			<font>
				<Font name="System Bold" size="18.0" />
			</font>
			<padding>
				<Insets left="12.0" />
			</padding>
		</Label>
		<Label alignment="TOP_LEFT" layoutX="20.0" layoutY="107.0" prefHeight="52.0" prefWidth="122.0" text="Feedback :" textFill="WHITE">
			<font>
				<Font name="System Bold" size="18.0" />
			</font>
			<padding>
				<Insets left="12.0" />
			</padding>
		</Label>
		<Label alignment="TOP_LEFT" layoutX="20.0" layoutY="81.0" prefHeight="52.0" prefWidth="122.0" text="Help :" textFill="WHITE">
			<font>
				<Font name="System Bold" size="18.0" />
			</font>
			<padding>
				<Insets left="12.0" />
			</padding>
		</Label>
		<StackPane fx:id="stackPane" layoutX="23.0" layoutY="184.0" prefHeight="555.0" prefWidth="1066.0" style="-fx-background-insets: 0;">
			<children>
				<ListView fx:id="displayList" prefHeight="501.0" prefWidth="1066.0" style="-fx-background-insets: 0;" styleClass="displayList" />
				<ListView fx:id="calendarList" prefHeight="447.0" prefWidth="1066.0" style="-fx-background-insets: 0;" />
			</children>
		</StackPane>
		<AnchorPane fx:id="hiddenMenu" layoutX="-335.0" layoutY="215.0" prefHeight="448.0" prefWidth="328.0" style="-fx-background-color: white;">
			<children>
				<VBox layoutX="64.0" layoutY="21.0" prefHeight="90.0" prefWidth="200.0">
					<children>
						<Label fx:id="Summary" alignment="CENTER" contentDisplay="CENTER" minWidth="-Infinity" prefHeight="27.0" prefWidth="200.0" style="-fx-underline: true; -fx-font-weight: bold;" text="SUMMARY" textFill="#616060">
							<font>
								<Font size="24.0" />
							</font>
						</Label>
						<Label alignment="CENTER" contentDisplay="CENTER" maxHeight="-Infinity" minHeight="-Infinity" minWidth="-Infinity" prefHeight="10.0" prefWidth="200.0" style="-fx-underline: true; -fx-font-weight: bold;" textFill="#616060">
							<font>
								<Font size="10.0" />
							</font>
						</Label>
						<HBox prefHeight="0.0" prefWidth="200.0">
							<children>
								<Label prefHeight="0.0" prefWidth="176.0" text="OVERDUE TASKS:" textFill="#6b6a6a" />
								<Label fx:id="overdueLabel" alignment="CENTER_RIGHT" prefHeight="27.0" prefWidth="23.0" text="0" textFill="#6b6a6a" />
							</children>
						</HBox>
						<HBox prefHeight="0.0" prefWidth="200.0">
							<children>
								<Label prefHeight="0.0" prefWidth="177.0" text="COMPLETED TASKS:" textFill="#6b6a6a" />
								<Label fx:id="completedLabel" alignment="CENTER_RIGHT" prefHeight="27.0" prefWidth="22.0" text="0" textFill="#6b6a6a" />
							</children>
						</HBox>
						<HBox prefHeight="0.0" prefWidth="200.0">
							<children>
								<Label prefHeight="0.0" prefWidth="177.0" text="REMAINING TASKS:" textFill="#6b6a6a" />
								<Label fx:id="remainingLabel" alignment="CENTER_RIGHT" prefHeight="27.0" prefWidth="22.0" text="0" textFill="#6b6a6a" />
							</children>
						</HBox>
					</children>
				</VBox>
				<PieChart fx:id="pieChart" layoutX="1.0" layoutY="139.0" prefHeight="259.0" prefWidth="326.0" />
			</children>
			<effect>
				<DropShadow color="#000000bf" offsetX="2.0" offsetY="2.0" />
			</effect>
		</AnchorPane>
	</children>
	<effect>
		<DropShadow />
	</effect>
</fx:root>
```
