
document.addEventListener("DOMContentLoaded", function () {
    const orderButtons = document.querySelectorAll(".pricebtn1");

    orderButtons.forEach(button => {
        button.addEventListener("click", function () {
            alert("add to your cart!");
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const socialLinks = document.querySelectorAll(".social-links");

    socialLinks.forEach(link => {
        link.addEventListener("click", function (e) {
            e.preventDefault();
            window.open(e.target.getAttribute("href"), "_blank");
        });
    });
});
