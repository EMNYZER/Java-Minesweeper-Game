-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 07 Des 2023 pada 03.28
-- Versi server: 10.4.28-MariaDB
-- Versi PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `minesweeper`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `cell`
--

CREATE TABLE `cell` (
  `Id` int(50) NOT NULL,
  `Content` varchar(1000) NOT NULL,
  `Mine` tinyint(1) NOT NULL,
  `Surrounding_Mine` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `game_state`
--

CREATE TABLE `game_state` (
  `Id` int(50) NOT NULL,
  `Timer` int(255) NOT NULL,
  `minute` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `score`
--

CREATE TABLE `score` (
  `Id` int(50) NOT NULL,
  `Games_Played` int(50) NOT NULL,
  `Games_won` int(50) NOT NULL,
  `LWStreak` int(50) NOT NULL,
  `LLStreak` int(50) NOT NULL,
  `CStreak` int(50) NOT NULL,
  `CWStreak` int(50) NOT NULL,
  `CLStreak` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Struktur dari tabel `users`
--

CREATE TABLE `users` (
  `id` int(50) NOT NULL,
  `nama` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `cell`
--
ALTER TABLE `cell`
  ADD KEY `Id` (`Id`);

--
-- Indeks untuk tabel `game_state`
--
ALTER TABLE `game_state`
  ADD KEY `Id` (`Id`);

--
-- Indeks untuk tabel `score`
--
ALTER TABLE `score`
  ADD KEY `Id` (`Id`);

--
-- Indeks untuk tabel `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `users`
--
ALTER TABLE `users`
  MODIFY `id` int(50) NOT NULL AUTO_INCREMENT;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `cell`
--
ALTER TABLE `cell`
  ADD CONSTRAINT `cell_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `users` (`id`);

--
-- Ketidakleluasaan untuk tabel `game_state`
--
ALTER TABLE `game_state`
  ADD CONSTRAINT `game_state_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `users` (`id`);

--
-- Ketidakleluasaan untuk tabel `score`
--
ALTER TABLE `score`
  ADD CONSTRAINT `score_ibfk_1` FOREIGN KEY (`Id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
