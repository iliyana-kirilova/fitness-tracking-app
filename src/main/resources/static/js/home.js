document.addEventListener("DOMContentLoaded", () => {
    const profileModal = document.getElementById("profileModal");
    const openProfileBtn = document.getElementById("openProfileModalBtn");
    const closeProfileBtn = document.getElementById("closeProfileModalBtn");
    const cancelProfileBtn = document.getElementById("cancelProfileBtn");

    // Отваряне
    if (openProfileBtn && profileModal) {
        openProfileBtn.addEventListener("click", () => {
            profileModal.classList.add("active");
        });
    }

    // Затваряне
    const closeProfileModal = () => {
        if (profileModal) profileModal.classList.remove("active");
    };

    if (closeProfileBtn) closeProfileBtn.addEventListener("click", closeProfileModal);
    if (cancelProfileBtn) cancelProfileBtn.addEventListener("click", closeProfileModal);

    // Затваряне при клик встрани
    if (profileModal) {
        profileModal.addEventListener("click", (e) => {
            if (e.target === profileModal) closeProfileModal();
        });
    }
});