import { AppLayout } from "../components/layout/AppLayout";
import MoviesCards from "../components/movies/MoviesCards";
import WatchedMoviesCards from "../components/movies/WatchedMoviesCards";
import MoviesNavbar from "../components/navigation/MoviesNavbar";

export default function WatchedMovies() {
  return (
    <>
      <MoviesNavbar />
      <WatchedMoviesCards />
    </>
  );
}
