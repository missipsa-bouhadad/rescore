// Calls to the backend API (through Vite in dev)

export type Residence = {
  id: number;
  name: string;
  address: string;
  type: "CROUS" | "PRIVATE";
};

export type RegisterData = {
  firstName: string;
  email: string;
  password: string;
  residenceId: number;
};

// Error returned by the server
export class ApiError extends Error {
  status: number;

  constructor(status: number, message: string) {
    super(message);
    this.status = status;
  }
}

async function request<T>(url: string, options?: RequestInit): Promise<T> {
  const response = await fetch(url, {
    headers: { "Content-Type": "application/json" },
    ...options,
  });

  if (!response.ok) {
    // Read the error message sent by the server
    const body = await response.json().catch(() => ({}));
    throw new ApiError(response.status, body.detail ?? "");
  }
  return response.json();
}

export function getResidences(city: string) {
  return request<Residence[]>(`/api/${city}/residences`);
}

export function register(data: RegisterData) {
  return request<unknown>("/api/auth/register", {
    method: "POST",
    body: JSON.stringify(data),
  });
}
