import form from "./form.js";

export default function home() {
  const view = document.querySelector("#view-home");
  view.removeAttribute("aria-hidden");
  view.removeAttribute("inert");

  const cards = view.querySelectorAll(".card");

  const partecipateButtons = view.querySelectorAll(".cta");

  partecipateButtons.forEach((btn) => {
    btn.addEventListener("click", (e) => {
      e.preventDefault();
      view.classList.remove("active");
      console.log(btn);
      form();
    });
  });

  const fadeInLeft = view.querySelectorAll(".fade-in-left");
  const fadeInTop = view.querySelectorAll(".fade-in-top");
  const fadeInBottom = view.querySelectorAll(".fade-in-bottom");
  const fadeInRight = view.querySelectorAll(".fade-in-right");

  const animatableOnEntranceObjs = [
    ...cards,
    ...fadeInLeft,
    ...fadeInTop,
    ...fadeInBottom,
    ...fadeInRight,
  ];

  animatableOnEntranceObjs.forEach((el) => observer.observe(el));
}

export const observer = new IntersectionObserver(
  (entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        entry.target.classList.add("show");
      }
    });
  },
  {
    rootMargin: "0px 0px -20% 0px",
  }
);
