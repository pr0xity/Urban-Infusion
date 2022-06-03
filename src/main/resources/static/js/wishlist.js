import {implementProductCardFunctionality} from "./views/productCardView.js";
import {setIncrementCounter} from "./controllers/cartController.js";

implementProductCardFunctionality(true);
setIncrementCounter().finally();