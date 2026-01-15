export const movies = [
    {
        id: 1,
        title: "Dune: Part Two",
        poster: "https://image.tmdb.org/t/p/w500/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg",
        backdrop: "https://image.tmdb.org/t/p/original/xOMo8BRK7PfcJv9JCnx7s5hj0PX.jpg",
        rating: "PG-13",
        duration: "2h 46m",
        genre: ["Sci-Fi", "Adventure"],
        releaseDate: "2024-03-01",
        imdbRating: 8.8,
        description: "Paul Atreides unites with Chani and the Fremen while on a warpath of revenge against the conspirators who destroyed his family.",
        cast: [
            { name: "Timothée Chalamet", role: "Paul Atreides" },
            { name: "Zendaya", role: "Chani" },
            { name: "Rebecca Ferguson", role: "Lady Jessica" }
        ],
        trailer: "https://www.youtube.com/embed/Way9Dexny3w"
    },
    {
        id: 2,
        title: "Oppenheimer",
        poster: "https://image.tmdb.org/t/p/w500/8Gxv8gSFCU0XGDykEGv7zR1n2ua.jpg",
        backdrop: "https://image.tmdb.org/t/p/original/fm6KqXpk3M2HVveHwvkHIeHYDI6.jpg",
        rating: "R",
        duration: "3h 00m",
        genre: ["Biography", "Drama", "History"],
        releaseDate: "2023-07-21",
        imdbRating: 8.4,
        description: "The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb.",
        cast: [
            { name: "Cillian Murphy", role: "J. Robert Oppenheimer" },
            { name: "Emily Blunt", role: "Kitty Oppenheimer" },
            { name: "Matt Damon", role: "Leslie Groves" }
        ],
        trailer: "https://www.youtube.com/embed/uYPbbksJxIg"
    },
    {
        id: 3,
        title: "Inside Out 2",
        poster: "https://image.tmdb.org/t/p/w500/vpnVM9B6NMmQpWeZvzLvDESb2QY.jpg",
        backdrop: "https://image.tmdb.org/t/p/original/zgQQF04u3cMAZytnNd4pWh1coV3.jpg",
        rating: "PG",
        duration: "1h 36m",
        genre: ["Animation", "Family", "Comedy"],
        releaseDate: "2024-06-14",
        imdbRating: 7.9,
        description: "Teenager Riley's mind headquarters is undergoing a sudden demolition to make room for something entirely unexpected: new Emotions! Joy, Sadness, Anger, Fear and Disgust, who’ve long been running a successful operation by all accounts, aren’t sure how to feel when Anxiety shows up. And it looks like she’s not alone.",
        cast: [
            { name: "Amy Poehler", role: "Joy" },
            { name: "Maya Hawke", role: "Anxiety" },
            { name: "Kensington Tallman", role: "Riley" }
        ],
        trailer: "https://www.youtube.com/embed/LEjhY15eCx0"
    },
    {
        id: 4,
        title: "Civil War",
        poster: "https://image.tmdb.org/t/p/w500/sh7Rg8Er3tFcN9BpKIPOMvALgZd.jpg",
        backdrop: "https://image.tmdb.org/t/p/original/s999Pr0vFp0H84J0Q26e8.jpg",
        rating: "R",
        duration: "1h 49m",
        genre: ["Action", "Thriller"],
        releaseDate: "2024-04-12",
        imdbRating: 7.6,
        description: "A journey across a dystopian future America, following a team of military-embedded journalists as they race against time to reach DC before rebel factions descend upon the White House.",
        cast: [
            { name: "Kirsten Dunst", role: "Lee" },
            { name: "Wagner Moura", role: "Joel" },
            { name: "Cailee Spaeny", role: "Jessie" }
        ],
        trailer: "https://www.youtube.com/embed/aDyQxtgKWBS"
    },
    {
        id: 5,
        title: "Kingdom of the Planet of the Apes",
        poster: "https://image.tmdb.org/t/p/w500/gKkl37BQuKTanygYQG1pyYgLVgf.jpg",
        backdrop: "https://image.tmdb.org/t/p/original/fqv8v6AycXKsivp1T5yKtLbGXce.jpg",
        rating: "PG-13",
        duration: "2h 25m",
        genre: ["Action", "Sci-Fi"],
        releaseDate: "2024-05-10",
        imdbRating: 7.3,
        description: "Many years after the reign of Caesar, a young ape goes on a journey that will lead him to question everything he's been taught about the past and make choices that will define a future for apes and humans alike.",
        cast: [
            { name: "Owen Teague", role: "Noa" },
            { name: "Freya Allan", role: "Mae" },
            { name: "Kevin Durand", role: "Proximus Caesar" }
        ],
        trailer: "https://www.youtube.com/embed/XtFI7SNtVpY"
    },
    {
        id: 6,
        title: "Deadpool & Wolverine",
        poster: "https://image.tmdb.org/t/p/w500/8cdWjvZQUExUUTzyp4t6EDMubfO.jpg",
        backdrop: "https://image.tmdb.org/t/p/original/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
        rating: "R",
        duration: "2h 07m",
        genre: ["Action", "Comedy", "Sci-Fi"],
        releaseDate: "2024-07-26",
        imdbRating: 8.2,
        description: "A listless Wade Wilson toils away in civilian life with his days as the morally flexible mercenary, Deadpool, behind him. But when his homeworld faces an existential threat, Wade must reluctantly suit-up again with an even more reluctant Wolverine.",
        cast: [
            { name: "Ryan Reynolds", role: "Deadpool" },
            { name: "Hugh Jackman", role: "Wolverine" },
            { name: "Emma Corrin", role: "Cassandra Nova" }
        ],
        trailer: "https://www.youtube.com/embed/73_1biulkYk"
    }
];

export const showtimes = [
    { id: 101, movieId: 1, time: "10:30 AM", hall: "IMAX 1", price: 18.50 },
    { id: 102, movieId: 1, time: "02:15 PM", hall: "IMAX 1", price: 18.50 },
    { id: 103, movieId: 1, time: "06:00 PM", hall: "IMAX 1", price: 20.00 },
    { id: 104, movieId: 2, time: "11:00 AM", hall: "Hall 3", price: 14.00 },
    { id: 105, movieId: 2, time: "03:30 PM", hall: "Hall 3", price: 14.00 },
    { id: 106, movieId: 3, time: "05:00 PM", hall: "Hall 5", price: 16.00 },
    { id: 107, movieId: 3, time: "08:30 PM", hall: "Hall 5", price: 18.00 },
    { id: 108, movieId: 4, time: "12:00 PM", hall: "Hall 2", price: 15.00 },
    { id: 109, movieId: 4, time: "04:30 PM", hall: "Hall 2", price: 15.00 },
    { id: 110, movieId: 5, time: "01:00 PM", hall: "Hall 1", price: 16.00 },
    { id: 111, movieId: 5, time: "05:00 PM", hall: "Hall 1", price: 18.00 },
    { id: 112, movieId: 6, time: "02:00 PM", hall: "IMAX 2", price: 19.00 },
    { id: 113, movieId: 6, time: "07:00 PM", hall: "IMAX 2", price: 22.00 }
];

export const currentUser = {
    name: "John Doe",
    email: "john@example.com",
    isLoggedIn: false,
    bookings: []
};

export const currentBooking = {
    details: null
};
