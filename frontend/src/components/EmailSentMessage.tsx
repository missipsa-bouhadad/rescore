import type { ReactNode } from "react";

type EmailSentMessageProps = {
  children: ReactNode;
};

function EmailSentMessage({ children }: EmailSentMessageProps) {
  return (
    <div className="flex flex-col items-center text-center">
      {/* Envelope icon */}
      <span className="flex size-16 items-center justify-center rounded-full bg-primary/10 text-primary">
        <svg
          className="size-8"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2.5"
          strokeLinecap="round"
          strokeLinejoin="round"
          aria-hidden="true"
        >
          <rect x="3" y="5" width="18" height="14" rx="2" />
          <polyline points="3 7 12 13 21 7" />
        </svg>
      </span>
      {/* Content */}
      <p className="mt-4 text-slate-600">{children}</p>
    </div>
  );
}

export default EmailSentMessage;
