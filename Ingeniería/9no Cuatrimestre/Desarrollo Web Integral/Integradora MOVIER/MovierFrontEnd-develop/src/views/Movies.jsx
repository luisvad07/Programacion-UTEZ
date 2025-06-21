import { AppLayout } from "../components/layout/AppLayout";
import MoviesCards from "../components/movies/MoviesCards";
import MoviesNavbar from "../components/navigation/MoviesNavbar";

export default function Movies() {
  return (
    <>
      <MoviesNavbar />
      <MoviesCards />
    </>
  );
}
