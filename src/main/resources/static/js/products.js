/**
 * A filter button class with selection toggle functionality.
 */
class FilterButton {
    activeColor = "#588157";
    inactiveColor = "#0000004d";

    constructor(buttonId) {
        this.selected = false;
        this.reference = document.getElementById(buttonId);
        this.reference.onclick = this.setSelected(this);
    }

    /**
     * Clear the previously selected button (if any) and set this button as selected on mouse click.
     *
     * @param selectedButton
     */
    setSelected = (selectedButton) => () => {
        if (!selectedButton.selected) {
            for (const button of buttons) {
                if (button.selected) {
                    button.toggleSelected(button);
                }
            }
        }
        selectedButton.toggleSelected()
    }

    /**
     * Set the button selected state as true if previously false, and vice versa.
     */
    toggleSelected() {
        this.selected = !this.selected;
        this.updateColor();
    }

    /**
     * Set the button background color according to its selected state.
     */
    updateColor() {
        if (this.selected) {
            this.reference.style.backgroundColor = this.activeColor;
        } else {
            this.reference.style.backgroundColor = this.inactiveColor;
        }
    }
}

const buttons = [
    new FilterButton('all'),
    new FilterButton('tea'),
    new FilterButton('accessories'),
];
