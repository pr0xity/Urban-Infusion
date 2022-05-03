"use strict";

const open_modal = document.querySelector(".open_modal");
const checkout_container = document.querySelector(".checkout__complete");
const checkout_overlay = document.querySelector(".checkout__complete--overlay");
const close_modal = document.querySelector(".checkout__btn--close");
const address = document.querySelector(".checkout__info--address").innerText;
const mapMarker = L.divIcon({ className: "marker-icon" });

const createAddressQueryParam = function () {
  return address.replaceAll(" ", "+").replaceAll(",", "");
};

addListeners();

function addListeners() {
  open_modal.addEventListener("click", () => {
    checkout_container.classList.add("show");
    checkout_overlay.classList.add("show");
  });
  close_modal.addEventListener("click", () => {
    checkout_container.classList.remove("show");
    checkout_overlay.classList.remove("show");
  });
}

const renderMap = function (lat, lon) {
  var map = L.map("map").setView([lat, lon], 17);
  // L.marker([lat, lon], { icon: mapMarker }).addTo(map);

  var greenIcon = new L.Icon({
    iconUrl: "../img/icons/mapmarker.svg",
    shadowUrl: "../img/icons/shadow.svg",
    iconSize: [41, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [61, 51],
  });

  L.marker([lat, lon], { icon: greenIcon }).addTo(map);

  L.tileLayer(
    "https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.png",
    {
      maxZoom: 20,
      attribution:
        '&copy; <a href="https://stadiamaps.com/">Stadia Maps</a>, &copy; <a href="https://openmaptiles.org/">OpenMapTiles</a> &copy; <a href="http://openstreetmap.org">OpenStreetMap</a> contributors',
    }
  ).addTo(map);
};

const getAddress = async function (addressParams) {
  const [data] = await getJSON(
    `https://nominatim.openstreetmap.org/search?q=${addressParams}&format=json`
  );
  let latitude;
  let longitude;
  try {
    console.log(data);
    latitude = data.lat;
    longitude = data.lon;
    const address = data.display_name;
    console.log(latitude, longitude, address);
  } catch (e) {
    console.log(e);
  }
  renderMap(latitude, longitude);
  return data;
};

const getJSON = function (url, errorMsg = "Something went wrong") {
  return fetch(url).then((response) => {
    if (!response.ok) throw new Error(`${errorMsg} (${response.status})`);

    return response.json();
  });
};

getAddress(createAddressQueryParam());
