package net.haspamelodica.codex.codingevent.session;

public class ServerException extends Exception
{
	/** avoid warnings about serialization */
	private static final long serialVersionUID = 1L;

	private final Type type;

	public ServerException(Type type)
	{
		super(type.getMessage());
		this.type = type;
	}
	public ServerException(Type type, Throwable cause)
	{
		super(type.getMessage(), cause);
		this.type = type;
	}
	public ServerException(Type type, String messageExtra)
	{
		super(type.getMessage() + ": " + messageExtra);
		this.type = type;
	}
	public ServerException(Type type, String messageExtra, Throwable cause)
	{
		super(type.getMessage() + ": " + messageExtra, cause);
		this.type = type;
	}

	public static void throwForResponseIfNotSuccess(int responseOrEOF) throws ServerException
	{
		throwForResponseIfNotSuccess(checkNotEOF(responseOrEOF));
	}
	public static byte checkNotEOF(int readOrEOF) throws ServerException
	{
		if(readOrEOF == -1)
			throw new ServerException(Type.EOF);
		return (byte) readOrEOF;
	}
	public static void throwForResponseIfNotSuccess(byte response) throws ServerException
	{
		if(response == 'S')
			return;

		Type type = Type.forResponse(response);
		if(type == null)
			throw forUnexpectedByte(response);
		throw new ServerException(type);
	}
	public static ServerException forUnexpectedByte(byte response)
	{
		return new ServerException(Type.UNEXPECTED_BYTE, Byte.toString(response));
	}

	public Type getType()
	{
		return type;
	}

	public static enum Type
	{
		//Don't use S: it means success
		PASSWORD_INCORRECT('I', "Password incorrect. Maybe there is another user with the same username?"),
		USERNAME_INVALID('U', "Username invalid"),
		PASSWORD_INVALID('P', "Password invalid"),
		INCORRECT_STATE('N', "Incorrect state (start before login, move/look before start, solution before start)"),
		INVALID_REQUEST('R', "Invalid request"),
		TIMEOUT('T', "Timeout"),
		SERVER_EOF('F', "Unexpected EOF (server-side)"),
		INTERNAL_ERROR('E', "Internal error (server-side)"),
		UNEXPECTED_BYTE((char) -1, "Unexpected response from server"),
		EOF((char) -1, "Unexpected EOF"),
		IO_EXCEPTION((char) -1, "Unexpected IOException");

		private final byte		response;
		private final String	message;

		private Type(char response, String message)
		{
			this.response = (byte) response;
			this.message = message;
		}

		public byte getServerResponse()
		{
			return response;
		}
		public String getMessage()
		{
			return message;
		}

		public static Type forResponse(byte serverResponse)
		{
			for(Type type : values())
				if(type.getServerResponse() == serverResponse)
					return type;
			return null;
		}
	}
}
