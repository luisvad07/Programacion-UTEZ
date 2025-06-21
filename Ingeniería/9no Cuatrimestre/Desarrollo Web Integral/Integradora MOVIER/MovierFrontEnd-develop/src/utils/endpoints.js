const WatchedMovieFunction = "https://vz6cec9smk.execute-api.us-east-1.amazonaws.com/Prod/watched"

const UpdateMovieFunction = "https://plkzmhdc98.execute-api.us-east-1.amazonaws.com/Prod/update/" //id

const GetCommentsFunction = "https://w9cs1xxpmg.execute-api.us-east-1.amazonaws.com/Prod/getComment/" //id

const CreateMovieFunction = "https://plkzmhdc98.execute-api.us-east-1.amazonaws.com/Prod/create"

const DeleteCommentFunction = "https://w9cs1xxpmg.execute-api.us-east-1.amazonaws.com/Prod/delete"

const GetWatchedMovieFunction = "https://vz6cec9smk.execute-api.us-east-1.amazonaws.com/Prod/watched/user/" //id

const CreateUserFunction = "https://0giy9cnir9.execute-api.us-east-1.amazonaws.com/Prod/create_user"

const ChangeStatusMovieFunction = "https://plkzmhdc98.execute-api.us-east-1.amazonaws.com/Prod/status/" //id

const GetMovieFunction = "https://plkzmhdc98.execute-api.us-east-1.amazonaws.com/Prod/getAll" //Esta trae status 1 

const GetMovieByIdFunction = "https://plkzmhdc98.execute-api.us-east-1.amazonaws.com/Prod/getMovie/" //id/user_id

const SetPasswordFunction = "https://0giy9cnir9.execute-api.us-east-1.amazonaws.com/Prod/set_password"

const LoginFunction = "https://0giy9cnir9.execute-api.us-east-1.amazonaws.com/Prod/login"

const CreateCommentFunction = "https://w9cs1xxpmg.execute-api.us-east-1.amazonaws.com/Prod/create"

const GetAllMoviesFunction = "https://plkzmhdc98.execute-api.us-east-1.amazonaws.com/Prod/getAllMovies" //Esta trae status 0 y 1 

const SearchMoviesFunction = "https://plkzmhdc98.execute-api.us-east-1.amazonaws.com/Prod/searchMovies/";



export default {
  WatchedMovieFunction,
  UpdateMovieFunction,
  GetCommentsFunction,
  CreateMovieFunction,
  DeleteCommentFunction,
  GetWatchedMovieFunction,
  CreateUserFunction,
  ChangeStatusMovieFunction,
  GetMovieFunction,
  GetMovieByIdFunction,
  SetPasswordFunction,
  LoginFunction,
  CreateCommentFunction,
  GetAllMoviesFunction,
  SearchMoviesFunction
}