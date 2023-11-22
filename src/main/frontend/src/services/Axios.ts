import axios, { AxiosError } from "axios";
import TokenService from "@/services/TokenService";
import { StatusCodes } from 'http-status-codes';

export const baseURL = process.env.NODE_ENV === 'development' ? "http://localhost:5173/" : "https://jule-und-joshua.de/";

const HttpClient = axios.create({
  baseURL: baseURL,
  headers: {
    "x-app-version": "1.0.0"
  }
});

interface RFC7807Problem {
  type: string;
  title: string;
  status: number;
  detail: string;
  instance?: string;
  attributes?: { [key: string]: string };
}

export class APIError extends Error {

  public statusCodes: StatusCodes;
  public problem: RFC7807Problem;

  constructor(message: string, statusCodes: StatusCodes, problem:RFC7807Problem) {
    super(message);
    this.statusCodes = statusCodes;
    this.problem = problem;
  }
}

export class AuthError extends Error {
  public statusCodes: StatusCodes;

  constructor(message: string, statusCodes: StatusCodes) {
    super(message);
    this.statusCodes = statusCodes;
  }
}

export class ServerError extends Error {
  public statusCodes: StatusCodes;

  constructor(message: string, statusCodes: StatusCodes) {
    super(message);
    this.statusCodes = statusCodes;
  }
}

HttpClient.interceptors.request.use(async function (config) {
  if (await TokenService.localTokenSet()) {
    const token = await TokenService.getLocalToken();
    config.headers.Authorization = "Bearer " + token;
  }
  return config;
}, async function (error) {
  return error;
});

HttpClient.interceptors.response.use(
  function (response) {
    return response;
  },
  function (error: AxiosError<RFC7807Problem>) {
    if (error.response!.status >= 400 && error.response!.status < 500) {
      if(error.response!.status == 401)
        return Promise.reject(new AuthError(error.message, StatusCodes.UNAUTHORIZED));
      return Promise.reject(new APIError(error.message, error.response!.status, error.response!.data));
    } else {
      return Promise.reject(new ServerError(error.message, StatusCodes.INTERNAL_SERVER_ERROR));
    }
  }
);

export default HttpClient;