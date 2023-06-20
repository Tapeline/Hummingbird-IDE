package me.tapeline.hummingbird.ide;

import me.tapeline.carousellib.elements.verticaltoggle.CVerticalToggleButton;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Test {

    public static void main1(String[] args) {
        JFrame frame = new JFrame("test");
        JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
//        JToolBar toolBar = new JToolBar(JToolBar.VERTICAL);
//        root.add(toolBar);
        JPanel toolBar = new JPanel();
        //toolBar.setLayout(new GridLayout(0, 1));
        toolBar.setLayout(new BoxLayout(toolBar, BoxLayout.PAGE_AXIS));
        root.add(toolBar, BorderLayout.LINE_START);

        CVerticalToggleButton button = new CVerticalToggleButton(null, "Test", false);
        toolBar.add(button);

        frame.setContentPane(root);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main2(String[] args) {
        List<String> colors = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : UIManager.getDefaults().entrySet()) {
            if (entry.getValue() instanceof Border) {
                colors.add((String) entry.getKey()); // all the keys are strings
            }
        }
        Collections.sort(colors);
        for (String name : colors)
            System.out.println(name);
    }

    public static void main(String[] args) {

    }

    /*
    * C:\Users\Tapeline\.jdks\openjdk-17.0.2\bin\java.exe "-javaagent:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2021.3.2\lib\idea_rt.jar=57479:C:\Program Files\JetBrains\IntelliJ IDEA Community Edition 2021.3.2\bin" -Dfile.encoding=UTF-8 -classpath F:\Программирование\Java\IntellijIdea\Hummingbird-IDE\target\classes;C:\Users\Tapeline\.m2\repository\commons-io\commons-io\2.11.0\commons-io-2.11.0.jar;C:\Users\Tapeline\.m2\repository\org\yaml\snakeyaml\2.0\snakeyaml-2.0.jar;C:\Users\Tapeline\.m2\repository\com\formdev\flatlaf\3.0\flatlaf-3.0.jar;C:\Users\Tapeline\.m2\repository\com\fifesoft\autocomplete\3.2.0\autocomplete-3.2.0.jar;C:\Users\Tapeline\.m2\repository\com\fifesoft\rsyntaxtextarea\3.2.0\rsyntaxtextarea-3.2.0.jar me.tapeline.hummingbird.ide.Test
Button.background
Button.darkShadow
Button.disabledText
Button.disabledToolBarBorderBackground
Button.focus
Button.foreground
Button.highlight
Button.light
Button.select
Button.shadow
Button.toolBarBorderBackground
CheckBox.background
CheckBox.disabledText
CheckBox.focus
CheckBox.foreground
CheckBoxMenuItem.acceleratorForeground
CheckBoxMenuItem.acceleratorSelectionForeground
CheckBoxMenuItem.background
CheckBoxMenuItem.disabledForeground
CheckBoxMenuItem.foreground
CheckBoxMenuItem.selectionBackground
CheckBoxMenuItem.selectionForeground
Checkbox.select
ColorChooser.background
ColorChooser.foreground
ColorChooser.swatchesDefaultRecentColor
ComboBox.background
ComboBox.buttonBackground
ComboBox.buttonDarkShadow
ComboBox.buttonHighlight
ComboBox.buttonShadow
ComboBox.disabledBackground
ComboBox.disabledForeground
ComboBox.foreground
ComboBox.selectionBackground
ComboBox.selectionForeground
Desktop.background
DesktopIcon.background
DesktopIcon.foreground
EditorPane.background
EditorPane.caretForeground
EditorPane.foreground
EditorPane.inactiveForeground
EditorPane.selectionBackground
EditorPane.selectionForeground
FormattedTextField.background
FormattedTextField.caretForeground
FormattedTextField.foreground
FormattedTextField.inactiveBackground
FormattedTextField.inactiveForeground
FormattedTextField.selectionBackground
FormattedTextField.selectionForeground
InternalFrame.activeTitleBackground
InternalFrame.activeTitleForeground
InternalFrame.borderColor
InternalFrame.borderDarkShadow
InternalFrame.borderHighlight
InternalFrame.borderLight
InternalFrame.borderShadow
InternalFrame.inactiveTitleBackground
InternalFrame.inactiveTitleForeground
Label.background
Label.disabledForeground
Label.disabledShadow
Label.foreground
List.background
List.dropCellBackground
List.dropLineColor
List.foreground
List.selectionBackground
List.selectionForeground
Menu.acceleratorForeground
Menu.acceleratorSelectionForeground
Menu.background
Menu.disabledForeground
Menu.foreground
Menu.selectionBackground
Menu.selectionForeground
MenuBar.background
MenuBar.borderColor
MenuBar.foreground
MenuBar.highlight
MenuBar.shadow
MenuItem.acceleratorForeground
MenuItem.acceleratorSelectionForeground
MenuItem.background
MenuItem.disabledForeground
MenuItem.foreground
MenuItem.selectionBackground
MenuItem.selectionForeground
OptionPane.background
OptionPane.errorDialog.border.background
OptionPane.errorDialog.titlePane.background
OptionPane.errorDialog.titlePane.foreground
OptionPane.errorDialog.titlePane.shadow
OptionPane.foreground
OptionPane.messageForeground
OptionPane.questionDialog.border.background
OptionPane.questionDialog.titlePane.background
OptionPane.questionDialog.titlePane.foreground
OptionPane.questionDialog.titlePane.shadow
OptionPane.warningDialog.border.background
OptionPane.warningDialog.titlePane.background
OptionPane.warningDialog.titlePane.foreground
OptionPane.warningDialog.titlePane.shadow
Panel.background
Panel.foreground
PasswordField.background
PasswordField.caretForeground
PasswordField.foreground
PasswordField.inactiveBackground
PasswordField.inactiveForeground
PasswordField.selectionBackground
PasswordField.selectionForeground
PopupMenu.background
PopupMenu.foreground
ProgressBar.background
ProgressBar.foreground
ProgressBar.selectionBackground
ProgressBar.selectionForeground
RadioButton.background
RadioButton.darkShadow
RadioButton.disabledText
RadioButton.focus
RadioButton.foreground
RadioButton.highlight
RadioButton.light
RadioButton.select
RadioButton.shadow
RadioButtonMenuItem.acceleratorForeground
RadioButtonMenuItem.acceleratorSelectionForeground
RadioButtonMenuItem.background
RadioButtonMenuItem.disabledForeground
RadioButtonMenuItem.foreground
RadioButtonMenuItem.selectionBackground
RadioButtonMenuItem.selectionForeground
ScrollBar.background
ScrollBar.darkShadow
ScrollBar.foreground
ScrollBar.highlight
ScrollBar.shadow
ScrollBar.thumb
ScrollBar.thumbDarkShadow
ScrollBar.thumbHighlight
ScrollBar.thumbShadow
ScrollBar.track
ScrollBar.trackHighlight
ScrollPane.background
ScrollPane.foreground
Separator.background
Separator.foreground
Separator.highlight
Separator.shadow
Slider.altTrackColor
Slider.background
Slider.focus
Slider.foreground
Slider.highlight
Slider.shadow
Slider.tickColor
Spinner.background
Spinner.foreground
SplitPane.background
SplitPane.darkShadow
SplitPane.dividerFocusColor
SplitPane.highlight
SplitPane.shadow
SplitPaneDivider.draggingColor
TabbedPane.background
TabbedPane.borderHightlightColor
TabbedPane.contentAreaColor
TabbedPane.darkShadow
TabbedPane.focus
TabbedPane.foreground
TabbedPane.highlight
TabbedPane.light
TabbedPane.selectHighlight
TabbedPane.selected
TabbedPane.shadow
TabbedPane.tabAreaBackground
TabbedPane.unselectedBackground
Table.background
Table.dropCellBackground
Table.dropLineColor
Table.dropLineShortColor
Table.focusCellBackground
Table.focusCellForeground
Table.foreground
Table.gridColor
Table.selectionBackground
Table.selectionForeground
Table.sortIconColor
TableHeader.background
TableHeader.focusCellBackground
TableHeader.foreground
TextArea.background
TextArea.caretForeground
TextArea.foreground
TextArea.inactiveForeground
TextArea.selectionBackground
TextArea.selectionForeground
TextField.background
TextField.caretForeground
TextField.darkShadow
TextField.foreground
TextField.highlight
TextField.inactiveBackground
TextField.inactiveForeground
TextField.light
TextField.selectionBackground
TextField.selectionForeground
TextField.shadow
TextPane.background
TextPane.caretForeground
TextPane.foreground
TextPane.inactiveForeground
TextPane.selectionBackground
TextPane.selectionForeground
TitledBorder.titleColor
ToggleButton.background
ToggleButton.darkShadow
ToggleButton.disabledText
ToggleButton.focus
ToggleButton.foreground
ToggleButton.highlight
ToggleButton.light
ToggleButton.select
ToggleButton.shadow
ToolBar.background
ToolBar.borderColor
ToolBar.darkShadow
ToolBar.dockingBackground
ToolBar.dockingForeground
ToolBar.floatingBackground
ToolBar.floatingForeground
ToolBar.foreground
ToolBar.highlight
ToolBar.light
ToolBar.shadow
ToolTip.background
ToolTip.backgroundInactive
ToolTip.foreground
ToolTip.foregroundInactive
Tree.background
Tree.dropCellBackground
Tree.dropLineColor
Tree.foreground
Tree.hash
Tree.line
Tree.selectionBackground
Tree.selectionBorderColor
Tree.selectionForeground
Tree.textBackground
Tree.textForeground
Viewport.background
Viewport.foreground
activeCaption
activeCaptionBorder
activeCaptionText
control
controlDkShadow
controlHighlight
controlLtHighlight
controlShadow
controlText
desktop
inactiveCaption
inactiveCaptionBorder
inactiveCaptionText
info
infoText
menu
menuText
scrollbar
text
textHighlight
textHighlightText
textInactiveText
textText
window
windowBorder
windowText

Process finished with exit code 0
*/

}
