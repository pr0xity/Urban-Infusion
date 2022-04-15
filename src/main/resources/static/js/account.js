const tag_types = ['email', 'password', 'address'];
const tags_array = ['modal', 'open', 'close', 'field', 'edit'];
const tags_map = createTagsMap();

const delete_tags = [
    document.getElementById('modal_delete'),
    document.querySelector('.account-info__btn--delete'),
    document.getElementById('close_delete'),
];

const forms = [
    document.querySelector('.modal__form--email'),
    document.querySelector('.modal__form--address'),
    document.querySelector('.modal__form--password'),
    document.querySelector('.modal__form--delete'),
];

addListeners();

/**
 * Create a map of tags to hold tag references.
 *
 * @returns {Map<string, any>} where key is tag type and value is tag reference
 */
function createTagsMap() {
    const map = new Map();
    for (const type of tag_types) {
        const tags = new Array(tags_array.length);
        for (const [index, element] of tags_array.entries()) {
            const tag_string = element + '_' + type;
            tags[index] = document.getElementById(tag_string);
        }
        map.set(type, tags);
    }
    return map;
}


/**
 * Carries out the creation of listeners.
 */
function addListeners() {
    for (const type of tag_types) {
        addEditableListeners(tags_map.get(type));
    }

    for (const form of forms) {
        addFormListener(form);
    }

    addDeleteAccountListeners(delete_tags);
}


/**
 * Add listeners to edit account modal windows.
 *
 * @param tags array of tag variables
 */
function addEditableListeners(tags) {
    let [modal, open, close, field, edit] = tags;
    open.addEventListener('click', () => {
        modal.classList.add('show');
    });
    close.addEventListener('click', () => {
        modal.classList.remove('show');
    });
    function updateField(event) {
        field.textContent = event.target.value;
    }
    edit.addEventListener('change', updateField);
}


/**
 * Add listeners for form events.
 *
 * @param form the modal form in question
 */
function addFormListener(form) {
    form.addEventListener('submit', (e) => {
        e.preventDefault();
    })
}


/**
 * Add listeners to open and close the account deletion modal window.
 *
 * @param tags array of tag variables
 */
function addDeleteAccountListeners(tags) {
    let [modal, open, close] = tags;
    open.addEventListener('click', () => {
        modal.classList.add('show');
    });
    close.addEventListener('click', () => {
        modal.classList.remove('show');
    });
}
