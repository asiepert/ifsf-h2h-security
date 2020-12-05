package ansix9242004.utils;

/**
 * <p>This extension to java.util.BitSet provides a "bitSize()" function
 * to better define the requested or desired size of the object, in order to
 * accommodate a more fixed-length paradigm.  Put more simply, if you declare the BitSet
 * to be 5 bits long, this "bitSize()" method will return 5, while the built-in method would
 * return the number of bits allocated for the BitSet which, depending on the implementation,
 * could very well be much larger.
 *
 * <p>The constructors and get(int, int) method are also overridden to ensure the
 * encapsulated environment to the user (i.e. the user will always receive and be using
 * this BitSet, not a java.util.BitSet, unless they explicitly ask for the latter).
 *
 * @author Software Verde: Andrew Groot
 * @author Software Verde: Josh Green
 */
public class BitSet extends java.util.BitSet {
	public static final int DEFAULT_SIZE = 8;
	private static final long serialVersionUID = 1L;
	private int size;

	public BitSet() {
		super(DEFAULT_SIZE);
		size = DEFAULT_SIZE;
	}

	public BitSet(int nbits) {
		super(nbits);
		size = nbits;
	}

	@Override
	public BitSet get(int low, int high) {
		BitSet n = new BitSet(high-low);
		for (int i=0; i < (high-low); i++) {
			n.set(i, this.get(low+i));
		}
		return n;
	}

	public int bitSize() {
		return size;
	}

	public static byte[] toByteArray(BitSet b) {
		int size = (int) Math.ceil(b.bitSize() / 8.0d);
		byte[] value = new byte[size];
		for (int i = 0; i < size; i++) {
			value[i] = toByte(b.get(i * 8, Math.min(b.bitSize(), (i + 1) * 8)));
		}
		return value;
	}

	public static byte toByte(BitSet b) {
		byte value = 0;
		for (int i = 0; i < b.bitSize(); i++) {
			if (b.get(i))
				value = (byte) (value | (1L << 7 - i));
		}
		return value;
	}

	public static BitSet toBitSet(final String value) {
		return ByteArrayUtils.toBitSet(StringUtils.toByteArray(value));
	}

	public static String toString(final BitSet value) {
		return ByteArrayUtils.toHex(toByteArray(value));
	}

	public static BitSet toBitSet(byte b) {
		BitSet bs = new BitSet(8);
		for (int i = 0; i < 8; i++) {
			if ((b & (1L << i)) > 0) {
				bs.set(7 - i);
			}
		}
		return bs;
	}

}